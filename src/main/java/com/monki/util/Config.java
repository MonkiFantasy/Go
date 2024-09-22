package com.monki.util;

public class Config {
    public static final int X=400;//棋盘左上角顶点x坐标
    public static final int Y=50;//棋盘左上角顶点y坐标
    public static final int LENGTH=792;//棋盘宽度
    public static final int PATH=19;//围棋路数
    public static final int SPACE=LENGTH/(PATH-1);
    public static int MODE=0;//对弈模式 0-单机对弈 1-联机对战
    public static Boolean SERVER=null;
}
