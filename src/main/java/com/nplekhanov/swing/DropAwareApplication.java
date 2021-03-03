package com.nplekhanov.swing;

import javax.swing.TransferHandler;

public interface DropAwareApplication extends Application {
    boolean importData(TransferHandler.TransferSupport support);
    boolean canImport(TransferHandler.TransferSupport support);
}
