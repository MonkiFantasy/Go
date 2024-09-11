package com.monki.util;

import java.awt.*;

public class Stone {
    private Position position;//棋子坐标
    private Color color;//棋子颜色
    private int count;//手数

    public Stone(int count, Color color, Position position) {
        this.count = count;
        this.color = color;
        this.position = position;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "position=" + position +
                ", color=" + color +
                ", count=" + count +
                '}';
    }
}