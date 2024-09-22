package com.monki.draw;

import com.monki.util.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class StartPanel extends JPanel {

    private MyButton offline;
    private MyButton online;

    public StartPanel(JFrame myframe){
        initPanel();
        initListener(myframe);

    }

    private void initListener(JFrame myframe) {
        offline.addActionListener(e -> {
            Config.MODE=0;
            myframe.remove(MyFrame.startPanel);
            myframe.setBounds(0,0,1880,950);
            myframe.add(MyFrame.myPanel);
        });
        online.addActionListener(e -> {
            Config.MODE=1;
            myframe.remove(MyFrame.startPanel);
            myframe.add(MyFrame.connectPanel);
            //WarningDialog warningDialog = new WarningDialog("正在开发中，敬请期待。。。");
            //int width=warningDialog.getWidth();
            //int height=warningDialog.getHeight();
            //warningDialog.setBounds((1920-width)/2,(1080-height)/2,width,height);
        });

    }

    private void initPanel() {
        setLayout(null);
        offline = new MyButton("单机模式");
        online= new MyButton("联机模式");
        setBounds(0,0,1000,500);
        offline.setBounds(150,100,200,50);
        online.setBounds(150,200,200,50);
        add(offline);
        add(online);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics gh) {
        //super.paintComponent(gh);
        Graphics2D g = (Graphics2D) gh;
        //background
        try {
            Image image = ImageIO.read(getClass().getResource("/img/img_1.png"));
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
