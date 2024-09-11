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

public class MyFrame extends JFrame {


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

        public MyFrame(String title){
            MyPanel myPanel = new MyPanel();

            //frame
            add(myPanel);
            setTitle(title);
            setLayout(null);
            setBounds(0,0,1920,1080);
            setBackground(Color.gray);
            setVisible(true);
        }





}

