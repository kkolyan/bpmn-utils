package com.nplekhanov.bpmn.realtime;

import com.nplekhanov.swing.Application;
import com.nplekhanov.swing.ApplicationEnv;
import com.nplekhanov.swing.DropAwareApplication;
import com.nplekhanov.swing.InteractiveCanvas;

import javax.swing.TransferHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DiagramVisualizerMain implements DropAwareApplication {
    public static void main(String[] args) {
        InteractiveCanvas.start(60,  new Dimension(1200, 800), new DiagramVisualizerMain());
    }

    private Application application;

    @Override
    public boolean importData(final TransferHandler.TransferSupport support) {
        try {
            List<?> transferData = (List<?>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            File file = (File) transferData.get(0);
            application = new DiagramVisualizingApplication(file);
            return true;
        } catch (UnsupportedFlavorException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean canImport(final TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public String getTitle() {
        if (application != null) {
            return application.getTitle();
        }
        return "";
    }

    @Override
    public void update(final Graphics2D canvas, final ApplicationEnv env) {
        if (application != null) {
            application.update(canvas, env);
        } else {
            canvas.setColor(Color.black);
            canvas.drawString("Drop BPMN file here", 20, 20);
        }
    }

    @Override
    public void destroy() {
        if (application != null) {
            application.destroy();
        }
    }
}
