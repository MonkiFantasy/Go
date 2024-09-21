package com.monki.draw;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectDialog extends JDialog {

    private JButton bt_connect;
    private JTextField tf_ip;
    private JTextField tf_port;

    ConnectDialog() {
        initDialog();
        initListener();
    }

    private void initListener() {
        bt_connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = tf_ip.getText();
                String port = tf_port.getText();
                System.out.println("ip:"+ip+" port:"+port);
            }
        });
    }

    private void initDialog() {
        setTitle("连接服务器");
        setContentPane(new BackgroundPanel("/img/img_button.png"));

        setLayout(null);
        tf_ip = new JTextField();
        JLabel lb_ip = new JLabel();
        tf_port = new JTextField();
        JLabel lb_port = new JLabel();
        bt_connect = new MyButton("点击连接");
        bt_connect.setFont(new Font("宋体", Font.BOLD, 15));
        bt_connect.setBounds(50,80,100,30);
        lb_ip.setBounds(20,20,20,20);
        lb_ip.setText("ip:");
        tf_ip.setBackground(Color.gray);
        tf_ip.setBounds(40,20,100,50);
        lb_port.setBounds(180,20,40,20);
        lb_port.setText("port:");
        tf_port.setBackground(Color.gray);
        tf_port.setBounds(220,20,100,50);
        setBounds(500,500,500,500);
        setModal(true);//模态会导致落子异常卡顿
        add(tf_ip);
        add(tf_port);
        add(lb_ip);
        add(lb_port);
        add(bt_connect);
        setVisible(true);

    }

    @Override
    public void paintComponents(Graphics g) {
        try {
            Image image = ImageIO.read(getClass().getResource("/img/img.png"));
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
