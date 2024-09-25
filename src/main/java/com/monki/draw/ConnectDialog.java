package com.monki.draw;

import com.monki.util.MyLogger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectDialog extends JPanel {

    private JButton bt_connect;
    private JTextField tf_ip;
    private JTextField tf_port;
    public  static String ip;
    public static int port;

    public ConnectDialog(JFrame myFrame) {
        initDialog();
        initListener(myFrame);
    }

    private void initListener(JFrame myFrame) {
        bt_connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("dasdasdas");
                MyLogger.log("按钮被点击",this.getClass());
                String textIp = tf_ip.getText();
                int textPort =Integer.parseInt(tf_port.getText());
                ip=textIp;
                port=textPort;
                System.out.println("ip:"+ip+" port:"+port);
                myFrame.remove(MyFrame.connectDialog);
                myFrame.add(MyFrame.connectPanel);
                myFrame.repaint();
            }
        });
    }

    private void initDialog() {
        //setTitle("连接服务器");
       // setContentPane(new BackgroundPanel("/img/img_button.png"));

        setLayout(null);
        setBounds(0,0,1000,500);
        //bt_connect = new MyButton();
        tf_ip = new JTextField();
        JLabel lb_ip = new JLabel();
        tf_port = new JTextField();
        JLabel lb_port = new JLabel();
        bt_connect = new MyButton("点击连接");
        bt_connect.setFont(new Font("宋体", Font.BOLD, 15));
        bt_connect.setBounds(150,200,200,50);
        lb_ip.setBounds(130,20,20,20);
        lb_ip.setText("IP地址:");
        tf_ip.setBackground(Color.gray);
        tf_ip.setBounds(150,20,200,50);
        lb_port.setBounds(110,70,40,20);
        lb_port.setText("端口:");
        tf_port.setBackground(Color.gray);
        tf_port.setBounds(150,70,200,50);
        //setBounds(500,500,500,500);

        //setModal(true);//模态会导致落子异常卡顿
        add(tf_ip);
        add(tf_port);
        add(lb_ip);
        add(lb_port);
        add(bt_connect);
        //setVisible(true);

    }

    @Override
    public void paintComponent(Graphics gh) {
        Graphics2D g = (Graphics2D) gh;
        try {
            Image image = ImageIO.read(getClass().getResource("/img/img.png"));
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
