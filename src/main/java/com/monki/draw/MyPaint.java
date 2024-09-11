package com.monki.draw;

import com.monki.util.Calculator;
import com.monki.util.Config;
import com.monki.util.Position;
import com.monki.util.Stone;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyPaint {
    public static final int X= Config.X;//棋盘左上角顶点x坐标
    public static final int Y=Config.Y;//棋盘左上角顶点y坐标
    public static final int LENGTH=Config.LENGTH;//棋盘宽度
    public static final int PATH=Config.PATH;//围棋路数
    public static final int SPACE=Config.SPACE;

    public void drawIndex(Graphics g) {
        char[] alphabet={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S'};
        g.setColor(Color.BLACK);
        g.setFont(new Font("Default", Font.PLAIN, 15));
        for (int i = 0; i < 19; i++) {
            g.drawString("" + alphabet[i], Config.X -Config.SPACE/8+ Config.SPACE * i, Config.Y-Config.SPACE/8);
            g.drawString("" + (i + 1), Config.X - Config.SPACE/2, Config.Y + Config.SPACE / 8 + Config.SPACE * i);
        }
    }
    public void drawLines(Graphics g) {
        //棋盘绘制
        g.setColor(new Color(242, 210, 17));
        g.fillRect(X-SPACE/2,Y-SPACE/2,LENGTH+SPACE,LENGTH+SPACE);
        g.setColor(Color.BLUE);
        //g.drawLine(X,Y+SPACE,X+LENGTH,Y+SPACE);
        //g.drawLine(X+SPACE,Y,X+SPACE,Y+LENGTH);

        for(int i=0;i<PATH;i++){
            g.drawLine(X,Y+SPACE*i,X+LENGTH,Y+SPACE*i);
            g.drawLine(X+SPACE*i,Y,X+SPACE*i,Y+LENGTH);

        }
        g.setColor(Color.BLACK);
    }
    public void drawStars(Graphics g) {

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
    }
    public void drawStone(Graphics g, Stone stone) {
        g.setColor(stone.getColor());
        g.fillOval(stone.getPosition().getI()-Config.SPACE/2,stone.getPosition().getJ()-Config.SPACE/2,SPACE,SPACE);
    }


}
