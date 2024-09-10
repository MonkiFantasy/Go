package com.monki.util;

public class Position {
    private int i;
    private int j;
    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public String toString() {
        return "当前落子坐标：i = " + i + ", j = " + j;
    }
}
