package com.nplekhanov.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class InteractiveCanvas {
    private static final AtomicLong counter = new AtomicLong();

    public interface InteractiveCanvasHandle {
        void stop();
    }

    private InteractiveCanvas() {
    }

    public static InteractiveCanvasHandle start(double framesPerSecond, Dimension windowSize, Application application) {
        JFrame frame = new JFrame("");
        Thread thread = new Thread(() -> {
            try {
                AtomicBoolean stop = new AtomicBoolean();
                frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                MouseTracker mouseTracker = new MouseTracker();
                KeyboardTracker keyboardTracker = new KeyboardTracker();
                JPanel canvasPanel = new JPanel() {
                    @Override
                    protected void paintComponent(final Graphics g) {
                        application.update((Graphics2D) g, new ApplicationEnv(frame.getWidth(), frame.getHeight(), mouseTracker, keyboardTracker));
                        mouseTracker.reset();
                        keyboardTracker.reset();
                    }
                };

                canvasPanel.addMouseListener(mouseTracker);
                canvasPanel.addMouseMotionListener(mouseTracker);
                canvasPanel.addMouseWheelListener(mouseTracker);

                canvasPanel.addKeyListener(keyboardTracker);

                if (application instanceof DropAwareApplication) {

                    canvasPanel.setTransferHandler(new TransferHandler("dsadsa") {
                        @Override
                        public boolean importData(final TransferSupport support) {
                            return ((DropAwareApplication) application).importData(support);
                        }

                        @Override
                        public boolean canImport(final TransferSupport support) {
                            return ((DropAwareApplication) application).canImport(support);
                        }
                    });
                }

                frame.add(canvasPanel);
                EventQueue.invokeLater(() -> {
                    frame.setVisible(true);
                    frame.setSize(windowSize.width, windowSize.height);
                });
                long frameNanos;
                if (framesPerSecond > 0) {
                    frameNanos = (long) (1000_000_000.0 / framesPerSecond);
                } else {
                    frameNanos = 0;
                }
                long lastFrameSync = System.nanoTime();
                while (!stop.get()) {
                    try {
                        EventQueue.invokeLater(() -> {
                            if (frame.isVisible()) {
                                if (!canvasPanel.hasFocus() && frame.isActive()) {
                                    canvasPanel.requestFocus();
                                }
                                String title = application.getTitle();
                                if (!title.equals(frame.getTitle())) {
                                    frame.setTitle(title);
                                }
                                frame.getContentPane().repaint();
                            } else {
                                stop.set(true);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    long nanosSpent = System.nanoTime() - lastFrameSync;
                    int nanosToSleep = (int) (frameNanos - nanosSpent);
                    lastFrameSync = System.nanoTime();
                    if (nanosToSleep > 0) {
                        int nanosInMs = 1000_000;
                        Thread.sleep(nanosToSleep / nanosInMs, nanosToSleep % nanosInMs);
                    }
                }
            } catch (InterruptedException ignored) {
                frame.setVisible(true);
            } finally {
                application.destroy();
            }
        });
        thread.setName("Canvas App " + counter.incrementAndGet());
        thread.start();
        return () -> {
            thread.interrupt();
            frame.setVisible(false);
        };
    }
}
