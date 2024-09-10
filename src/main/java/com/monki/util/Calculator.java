package com.monki.util;

public class Calculator {

    //通过鼠标指针坐标获得围棋落子坐标
    public static Position getIndexViaMouse(int mouseX, int mouseY){
        //计算i
        int i = (mouseX-Config.X+Config.SPACE/2)/Config.SPACE+1;
        //计算j
        int j = (mouseY-Config.Y+Config.SPACE/2)/Config.SPACE+1;
        return new Position(i, j);
    }

    //根据交叉点索引获得实际坐标
    public static Position getPositionViaIndex(int i, int j){
        int posX = Config.X+(i-1)*Config.SPACE;
        int posY = Config.Y+(j-1)*Config.SPACE;
        return new Position(posX,posY);

    }

}
