package com.monki.draw;

import com.monki.test.StoneClient;
import com.monki.test.StoneServer;
import com.monki.util.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ConnectPanel extends JPanel {

    private MyButton offline;
    private MyButton online;

    public ConnectPanel(JFrame myframe){
        initPanel();
        initListener(myframe);

    }

    private void initListener(JFrame myframe) {
        offline.addActionListener(e -> {
            Config.SERVER=true;
            //new Thread(new StoneServer()).start();
            myframe.remove(MyFrame.connectPanel);
            myframe.setBounds(0,0,1880,950);
            myframe.add(MyFrame.myPanel);
            new Thread(new StoneServer()).start();
        });
        online.addActionListener(e -> {
            Config.SERVER=false;
            //new Thread(new StoneClient()).start();
            myframe.remove(MyFrame.connectPanel);
            myframe.setBounds(0,0,1880,950);
            myframe.add(MyFrame.myPanel);
            new Thread(new StoneClient()).start();
            //WarningDialog warningDialog = new WarningDialog("正在开发中，敬请期待。。。");
            //int width=warningDialog.getWidth();
            //int height=warningDialog.getHeight();
            //warningDialog.setBounds((1920-width)/2,(1080-height)/2,width,height);
        });

    }

    private void initPanel() {
        setLayout(null);
        offline = new MyButton("创建房间");
        online= new MyButton("加入房间");
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
