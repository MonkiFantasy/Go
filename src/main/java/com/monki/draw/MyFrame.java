package com.monki.draw;

import com.monki.util.Config;
import com.monki.entity.Position;
import com.monki.entity.Stone;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame {


    public static final int X= Config.X;//棋盘左上角顶点x坐标
    public static final int Y=Config.Y;//棋盘左上角顶点y坐标
    public static final int LENGTH=Config.LENGTH;//棋盘宽度
    public static final int PATH=Config.PATH;//围棋路数
    public static final int SPACE=Config.SPACE;
    public static MyPaint myPaint = new MyPaint();
    public static Position mouseOn=null;//鼠标指针距离最近交叉点坐标
    public static List<Stone> fallOn=new ArrayList<>();//已落子的信息


        public MyFrame(String title){
            initFrame(title);
        }

    private void initFrame(String title) {
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

