package me.greencat;

import javax.swing.*;
import java.awt.*;

public class Introduce {
    private static final JFrame frame = new JFrame("LWEBus");
    private static final JLabel label = new JLabel("LWEBus is a light weight event bus that also provides a way to not use reflection");
    private static final JLabel label2 = new JLabel("More Information - https://github.com/ItzGreenCat/LWEBus");
    public static void main(String[] args) {
        frame.setSize(500,150);
        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.add(label2, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

