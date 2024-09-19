package com.monki.entity;

import com.monki.core.Board;
import com.monki.core.StoneString;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.io.Serializable;
import java.util.Set;

public class Stone implements Serializable {
    private static final long serialVersionUID = 1L;
    private int count;//手数
    private Color color;//棋子颜色
    private Position coordinate;//棋子坐标
    private Position index;
    private StoneString myString;//
    private Set<Position> liberty=new HashSet<>();//保存气的坐标
    private Boolean isRemoved;


    public Stone(int count, Color color, Position coordinate, Position index) {
        this.count = count;
        this.color = color;
        this.coordinate = coordinate;
        this.index = index;
        this.isRemoved=false;
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

    public Position getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Position coordinate) {
        this.coordinate = coordinate;
    }

    public Position getIndex() {
        return index;
    }
    public void setIndex(Position index) {
        this.index = index;
    }

    public Set<Position> getLiberty() {
        return liberty;
    }

    public void setLiberty(Set<Position> liberty) {
        this.liberty = liberty;
    }

    public StoneString getMyString() {
        return myString;
    }
    public void setMyString(StoneString myString) {
        this.myString = myString;
    }

    public void setRemoved(Boolean removed) {
        this.isRemoved = removed;
    }

    public Boolean getRemoved() {
        return this.isRemoved;
    }
    private void addLiberity(Position index){
        liberty.add(index);
    }
    //把当前棋子与已有棋串连接
    /*public void connectString(int player,StoneString string) {
        int i = index.getJ();
        int j = index.getI();
        Position up=new Position(i-1,j);
        Position down=new Position(i+1,j);
        Position left=new Position(i,j-1);
        Position right=new Position(i,j+1);
        if(true) {
            if (checkBoardState(up, player)) {
                string.addStone(this);
                myString = string;
            }
            if (checkBoardState(down, player)) {
                string.addStone(this);
                myString = string;
            }
            if (checkBoardState(left, player)) {
                string.addStone(this);
                myString = string;
            }
            if (checkBoardState(right, player)) {
                string.addStone(this);
                myString = string;
            }
        }*//*else{
            string.addStone(this);
            myString=string;
        }*//*

    }*/
    public void checkLiberty(){
        int i = index.getJ();
        int j = index.getI();
        Position up=new Position(i-1,j);
        Position down=new Position(i+1,j);
        Position left=new Position(i,j-1);
        Position right=new Position(i,j+1);
        if(checkBoardState(up,0)){
            addLiberity(up);
        }
        if (checkBoardState(down,0)){
            addLiberity(down);
        }
        if(checkBoardState(left,0)){
            addLiberity(left);
        }
        if (checkBoardState(right,0)){
            addLiberity(right);
        }

    }
    //index 在数组上的坐标 i 棋盘当前点的落子状态 -1黑 0空 1白
    private boolean checkBoardState(Position index, int state) {
        return isOnBoard(index)&&Board.state[index.getI()][index.getJ()]==state;
    }

    private boolean isOnBoard(Position index) {
        return index.getI()>=1&&index.getI()<=19&&index.getJ()>=1&&index.getJ()<=19;
    }


    @Override
    public String toString() {
        return "Stone{" +
                "count=" + count +
                ", color=" + color +
                ", coordinate=" + coordinate +
                ", index=" + index +
                ", myString=" + myString +
                ", \nliberty=" + liberty +
                ", isRemoved=" + isRemoved +
                '}';
    }

}