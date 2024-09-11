package com.monki.test;

import javax.swing.*;
import java.awt.*;

public class LayerExample extends JFrame {
    public LayerExample() {
        super("Layer Example");

        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JPanel foreground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                g2d.setColor(Color.RED);
                g2d.fillRect(50, 50, 100, 100);
            }
        };

        foreground.setBounds(0, 0, 200, 200);
        background.setLayout(null);
        background.add(foreground);

        this.add(background);
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new LayerExample();
    }
}