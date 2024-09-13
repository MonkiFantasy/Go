package com.monki.draw;

import com.monki.util.Calculator;
import com.monki.util.Config;
import com.monki.util.Position;
import com.monki.util.Stone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MyPanel extends JPanel {
    public static final int X= Config.X;//棋盘左上角顶点x坐标
    public static final int Y=Config.Y;//棋盘左上角顶点y坐标
    public static final int LENGTH=Config.LENGTH;//棋盘宽度
    public static final int PATH=Config.PATH;//围棋路数
    public static final int SPACE=Config.SPACE;
    public static MyPaint myPaint = new MyPaint();
    public static Position mouseOn=null;//鼠标指针距离最近交叉点坐标
    public static List<Stone> fallOn=new ArrayList<>();//已落子的信息
    public static int turn=-1;//-1黑 1白
    public static int count=1;//对弈手数
    public static int[][] board=new int[PATH+1][PATH+1];


    public MyPanel(){
        setLayout(null);
        setBounds(0,0,1920,1080);
        JButton dialog = new JButton("进入连接界面");
        dialog.setFont(new Font("宋体", Font.PLAIN, 20));
        dialog.setBounds(X,Y+LENGTH+SPACE,SPACE*4,SPACE);
        setDoubleBuffered(true);
        add(dialog);
        //mouse listener


        setBackground(Color.gray);addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(isInBoard(e.getX(),e.getY())){
                    mouseOn= Calculator.getIndexViaMouse(e.getX(),e.getY());
                    mouseOn=Calculator.getPositionViaIndex(mouseOn.getI(),mouseOn.getJ());
                    repaint();
                    //repaint(mouseOn.getI()- Config.SPACE/2,mouseOn.getJ()-Config.SPACE/2,SPACE,SPACE);
                }
            }
        });

        dialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jDialog = new JDialog();
                jDialog.setBounds(0,0,500,500);
                //jDialog.setModal(true);//模态会导致落子异常卡顿
                jDialog.setVisible(true);
            }
        });
        setVisible(true);
    }
    @Override
    public void paintComponent(Graphics gh) {
        super.paintComponent(gh);
        Graphics2D g = (Graphics2D) gh;
        //棋盘绘制
        myPaint.drawLines(g);
        myPaint.drawIndex(g);
        myPaint.drawStars(g);
        //Image image = new ImageIcon("img/1.png").getImage();
        //g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);


        //实现落子高亮效果
        if(mouseOn!=null){
            g.setColor(Color.RED);
            g.drawRect(mouseOn.getI()- Config.SPACE/2,mouseOn.getJ()-Config.SPACE/2,SPACE,SPACE);
        }

        //落子实现
        if(!fallOn.isEmpty()){
            g.setColor(Color.BLACK);
            Boolean isBlack = true;
            for (Stone stone : fallOn) {
                myPaint.drawStone(g,stone);
            }
        }
    }
    // 辅助方法，检查鼠标是否在棋盘区域内

    private boolean isInBoard(int x, int y) {

        return x >= X - Config.SPACE / 2 && x <= X + LENGTH + Config.SPACE / 2

                && y >= Y - Config.SPACE / 2 && y <= Y + LENGTH + Config.SPACE / 2;

    }
    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {

            e.getButton();
           /* System.out.println("-----------------------------------------------------------------");
            Position position = Calculator.getIndexViaMouse(e.getX(), e.getY());
            System.out.println("position:"+position);
            Position pos = Calculator.getPositionViaIndex(position.getI(), position.getJ());
            System.out.println("pos:"+pos);
            System.out.println("实际点击坐标"+e.getX()+" "+e.getY());

            System.out.println("-----------------------------------------------------------------");*/

            if(isInBoard(e.getX(),e.getY())){
                Position p = Calculator.getIndexViaMouse(e.getX(),e.getY());
                Position position1 = Calculator.getPositionViaIndex(p.getI(), p.getJ());
                Stone stone = new Stone(count, turn == -1 ? Color.BLACK : Color.WHITE, position1);
                turn=-turn;
                count++;
                fallOn.add(stone);
                //只重绘棋子附近的方形，以提高性能
                repaint(position1.getI()-Config.SPACE/2,position1.getJ()-Config.SPACE/2,SPACE*2,SPACE*2);
                System.out.println(stone.toString());
            }

        }
    }
}
