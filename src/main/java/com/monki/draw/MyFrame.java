package com.monki.draw;

import com.monki.util.Calculator;
import com.monki.util.Config;
import com.monki.util.Position;

import java.awt.*;
import java.awt.event.*;

public class MyFrame extends Frame {
        public static final int X= Config.X;//棋盘左上角顶点x坐标
        public static final int Y=Config.Y;//棋盘左上角顶点y坐标
        public static final int LENGTH=Config.LENGTH;//棋盘宽度
        public static final int PATH=Config.PATH;//围棋路数
        public static final int SPACE=Config.SPACE;


        public MyFrame(String title){
            Label top = new Label();
            //Label center = new Label();
            Label bottom = new Label();
            //frame
            Button button = new Button();
            bottom.add(new PopupMenu());
            setTitle(title);
            setLayout(null);
            setBounds(0,0,1920,1080);
            setBackground(Color.white);
            setVisible(true);
            //top
            top.setBounds(0,0,1920,100);
            top.setBackground(Color.gray);
            //center
            //center.setBounds(400,100,900,900);
            //center.setBackground(new Color(0xBD8A39));
            //bottom
            bottom.setBounds(0,1000,1920,80);
            bottom.setBackground(Color.gray);
            //add label
            add(top, BorderLayout.NORTH);
            add(bottom, BorderLayout.SOUTH);
            //add(center, BorderLayout.CENTER);

            //mouse listener
            addMouseListener(new MyMouseListener());
            //exit
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {

                    //System.out.println("mouse moving:"+e.getX()+" "+e.getY());
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            //棋盘绘制
            g.setColor(new Color(242, 210, 17));
            g.fillRect(X,Y,LENGTH,LENGTH);
            g.setColor(Color.BLUE);
            //g.drawLine(X,Y+SPACE,X+LENGTH,Y+SPACE);
            //g.drawLine(X+SPACE,Y,X+SPACE,Y+LENGTH);

            for(int i=0;i<PATH;i++){
                g.drawLine(X,Y+SPACE*i,X+LENGTH,Y+SPACE*i);
                g.drawLine(X+SPACE*i,Y,X+SPACE*i,Y+LENGTH);

            }
            g.setColor(Color.BLACK);
            //星位绘制
            Position position = Calculator.getPositionViaIndex(4, 4);
            g.fillOval(position.getI()-SPACE/10,position.getJ()-SPACE/10,SPACE/5,SPACE/5);
            position = Calculator.getPositionViaIndex(10, 4);
            g.fillOval(position.getI()-SPACE/10,position.getJ()-SPACE/10,SPACE/5,SPACE/5);
            position = Calculator.getPositionViaIndex(16, 4);
            g.fillOval(position.getI()-SPACE/10,position.getJ()-SPACE/10,SPACE/5,SPACE/5);
            position = Calculator.getPositionViaIndex(4, 10);
            g.fillOval(position.getI()-SPACE/10,position.getJ()-SPACE/10,SPACE/5,SPACE/5);
            position = Calculator.getPositionViaIndex(10, 10);
            g.fillOval(position.getI()-SPACE/10,position.getJ()-SPACE/10,SPACE/5,SPACE/5);
            position = Calculator.getPositionViaIndex(16, 10);
            g.fillOval(position.getI()-SPACE/10,position.getJ()-SPACE/10,SPACE/5,SPACE/5);
            position = Calculator.getPositionViaIndex(4, 16);
            g.fillOval(position.getI()-SPACE/10,position.getJ()-SPACE/10,SPACE/5,SPACE/5);
            position = Calculator.getPositionViaIndex(10, 16);
            g.fillOval(position.getI()-SPACE/10,position.getJ()-SPACE/10,SPACE/5,SPACE/5);
            position = Calculator.getPositionViaIndex(16, 16);
            g.fillOval(position.getI()-SPACE/10,position.getJ()-SPACE/10,SPACE/5,SPACE/5);
            //char[] alphabet={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S'};
            

            //g.drawChars(alphabet,0,alphabet.length,X,Y);

        }
    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            MyFrame frame=(MyFrame) e.getSource();
            System.out.println("-----------------------------------------------------------------");
            Position position = Calculator.getIndexViaMouse(e.getX(), e.getY());
            System.out.println("position:"+position);
            Position pos = Calculator.getPositionViaIndex(position.getI(), position.getJ());
            System.out.println("pos:"+pos);
            System.out.println("实际点击坐标"+e.getX()+" "+e.getY());
            //System.out.println(e.getXOnScreen()+" "+e.getYOnScreen());
            //frame.setLocation(e.getX(),e.getY());
            System.out.println("-----------------------------------------------------------------");


        }
    }
}

