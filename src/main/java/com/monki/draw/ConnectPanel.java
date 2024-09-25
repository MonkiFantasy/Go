package com.monki.draw;

import com.monki.socket.StoneClient;
import com.monki.socket.StoneServer;
import com.monki.util.Config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static com.monki.draw.MyFrame.connectDialog;

public class ConnectPanel extends JPanel {

    private MyButton offline;
    private MyButton online;
    //private ConnectDialog connectDialog;

    public ConnectPanel(JFrame myframe){
        initPanel();
        initListener(myframe);

    }

    private void initListener(JFrame myframe) {
        offline.addActionListener(e -> {
            Config.SERVER=true;
            //new Thread(new StoneServer()).start();
            //TODO:弹窗获取ip端口号，根据ip端口号创建服器和客户端

            Boolean condition = (ConnectDialog.port!=0);
            if(condition){
                myframe.remove(MyFrame.connectPanel);
                myframe.setBounds(0,0,1880,950);
                myframe.add(MyFrame.myPanel);
                myframe.repaint();
                new Thread(new StoneServer(ConnectDialog.port)).start();
            }else {
                myframe.remove(MyFrame.connectPanel);
                myframe.add(connectDialog);
                myframe.repaint();
                //connectDialog.setVisible(true);
            }

            //new Thread(new StoneServer(12345)).start();
        });
        online.addActionListener(e -> {
            Config.SERVER=false;
            //new Thread(new StoneClient()).start();

            //ConnectDialog connectDialog = new ConnectDialog();
            Boolean condition = (ConnectDialog.ip!=null&&ConnectDialog.port!=0);

            if(condition){
                myframe.remove(MyFrame.connectPanel);
                myframe.setBounds(0,0,1880,950);
                myframe.add(MyFrame.myPanel);
                myframe.repaint();
                new Thread(new StoneClient(ConnectDialog.ip,ConnectDialog.port)).start();
            }else {
                myframe.remove(MyFrame.connectPanel);
                myframe.add(connectDialog);
                myframe.repaint();
                //connectDialog.setVisible(true);
            }
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

        //setVisible(true);
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
