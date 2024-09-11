package com.monki.draw;

import com.monki.util.Calculator;
import com.monki.util.Config;
import com.monki.util.Position;
import com.monki.util.Stone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.monki.draw.MyPaint.SPACE;

public class MyFrame extends Frame {


    public static final int X= Config.X;//棋盘左上角顶点x坐标
    public static final int Y=Config.Y;//棋盘左上角顶点y坐标
    public static final int LENGTH=Config.LENGTH;//棋盘宽度
    public static final int PATH=Config.PATH;//围棋路数
    public static final int SPACE=Config.SPACE;

    public static Position mouseOn=null;//鼠标指针距离最近交叉点坐标
    public static List<Stone> fallOn=new ArrayList<>();//已落子的信息
    public static int turn=-1;//-1黑 1白
    public static int count=1;//对弈手数
    public static int[][] board=new int[PATH+1][PATH+1];

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
                    if(e.getX()>=X-SPACE/2&&e.getY()>=Y-SPACE/2&&e.getX()<=X+LENGTH+SPACE/2&&e.getY()<=Y+LENGTH+SPACE/2){
                        mouseOn=Calculator.getIndexViaMouse(e.getX(),e.getY());
                        mouseOn=Calculator.getPositionViaIndex(mouseOn.getI(),mouseOn.getJ());
                        //repaint();
                    }

                    //System.out.println("mouse moving:"+e.getX()+" "+e.getY());
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            MyPaint myPaint = new MyPaint();
            //棋盘绘制
            myPaint.drawLines(g);
            myPaint.drawIndex(g);
            myPaint.drawStars(g);


            //实现落子高亮效果
            if(mouseOn!=null){
                g.setColor(Color.RED);
                g.drawRect(mouseOn.getI()-Config.SPACE/2,mouseOn.getJ()-Config.SPACE/2,SPACE,SPACE);
            }

            //落子实现
            if(!fallOn.isEmpty()){
                g.setColor(Color.BLACK);
                Boolean isBlack = true;
                for (Stone stone : fallOn) {
                    myPaint.drawStone(g,stone);
                }
            }

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

            if(e.getX()>=X-SPACE/2&&e.getY()>=Y-SPACE/2&&e.getX()<=X+LENGTH+SPACE/2&&e.getY()<=Y+LENGTH+SPACE/2){
                Position p = Calculator.getIndexViaMouse(e.getX(),e.getY());
                Position position1 = Calculator.getPositionViaIndex(p.getI(), p.getJ());
                Stone stone = new Stone(count, turn == -1 ? Color.BLACK : Color.WHITE, position1);
                turn=-turn;
                count++;
                fallOn.add(stone);
                repaint();
                System.out.println(stone.toString());
            }

        }
    }
}

