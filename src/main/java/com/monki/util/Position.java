package com.monki.util;

import java.awt.*;
import java.io.Serializable;

public class Position implements Serializable {
    static final long serialVersionUID = 2L;
    private int i;
    private int j;
    private Color color;
    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Position(int i, int j, Color color) {
        this.i = i;
        this.j = j;
        this.color = color;
    }
    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "当前落子坐标：i = " + i + ", j = " + j;
    }
}
