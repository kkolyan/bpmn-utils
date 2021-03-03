package com.nplekhanov.swing;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

public class GenerateKeyboardEnum {
    public static void main(String[] args) throws Exception {
        for (final Field field : KeyEvent.class.getFields()) {
            if(field.getName().startsWith("VK_")) {
                int value = (int) field.get(null);
                System.out.printf("%s(0x%s),\n", field.getName().substring("VK_".length()), Integer.toHexString(value));
            }
        }
        Thread.sleep(1000);
    }
}
