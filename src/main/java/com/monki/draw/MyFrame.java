package com.monki.draw;

import javax.swing.*;
import java.awt.*;


public class MyFrame extends JFrame {


    public static JPanel myPanel;
    public static JPanel startPanel;
    public static JPanel connectPanel;

    public MyFrame(String title){
            initFrame(title);
        }

    private void initFrame(String title) {
        myPanel = new MyPanel(this);
        startPanel = new StartPanel(this);
        connectPanel = new ConnectPanel(this);
        //frame
        //Container contentPane = getContentPane();
        //add(myPanel);
        //contentPane.remove(myPanel);
        setTitle(title);
        setBounds(710,290,500,500);
        add(startPanel);
        setLayout(null);
        //setBounds(0,0,1920,1080);
        setBackground(Color.gray);
        setVisible(true);
    }


}

