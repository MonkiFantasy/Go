package com.monki.core;

import com.monki.entity.Position;
import com.monki.entity.Stone;

import java.util.*;

public class StoneString {
    private enum player{white,black};//棋串属于哪一方
    private int liberty;//气的数量
    private Set<Stone> stones=new HashSet<>();//当前棋串的棋子
    private Set<Position> libertyPos=new HashSet<>();//当前气的位置
    StoneString(){
    }

    public Set<Stone> getStones() {
        return stones;
    }

    public void addStones(Set<Stone> stones) {
        System.out.println("添加前"+this.stones);

        this.stones.addAll(stones);

        System.out.println("添加的"+stones);
        System.out.println("添加后"+this.stones);
        liberty=getLiberty();
    }

    public void addStone(Stone stone){

        System.out.println("添加前"+this.stones);
        stones.add(stone);

        System.out.println("添加的"+stones);
        System.out.println("添加后"+this.stones);
        liberty=getLiberty();
    }

    //依赖于棋串的棋子的气，要调用此方法，添加进来的棋子必须要已经添加了气的坐标
    public int getLiberty() {
//        System.out.println("---------------计算气-------------------------------");
        Set<Position> libertyOfString = new HashSet<>();
        libertyOfString.addAll(libertyPos);

        Set<Position> fallonIndex = new HashSet<>();
        for (Stone stone : stones) {
            Position index = stone.getIndex();
            index = new Position(index.getJ(),index.getI());
            //System.out.println("棋子坐标"+index);
            fallonIndex.add(index);
        }


        stones.forEach(stone -> {
            stone.checkLiberty();
            libertyPos.addAll(stone.getLiberty());
        });
        int sizeOFLiberty=libertyPos.size();
/*        System.out.println("被减数"+sizeOFLiberity);
        System.out.println(libertyOfString);


        System.out.println("集合1"+fallonIndex);
        System.out.println("集合2"+libertyOfString);*/
        libertyOfString.retainAll(fallonIndex);
/*        System.out.println("交集"+libertyOfString);

        System.out.println("减数"+libertyOfString.size());
        System.out.println(libertyOfString);

        System.out.println("-------------------计算气-------------------------");*/
        //利用集合去重
        return sizeOFLiberty-libertyOfString.size();
    }
    //敌方落子时，移除当前气坐标中对方落子的位置
    public void updateLiberty(Position index){
        Position target = new Position(index.getJ(), index.getI());
        libertyPos.removeIf(libertyPo -> libertyPo.equals(target));
        //this.liberty=;//TODO:获取气的时候重置了libertyPos
       /* Set<Position> libertyOfString = new HashSet<>();
        libertyOfString.addAll(libertyPos);
        Set<Position> fallonIndex = new HashSet<>();
        for (Stone stone : stones) {
            Position i = stone.getIndex();
            i = new Position(index.getJ(),index.getI());
            //System.out.println("棋子坐标"+index);
            fallonIndex.add(index);
        }
        int sizeOFLiberty=libertyPos.size();
        libertyOfString.retainAll(fallonIndex);
        this.liberty=sizeOFLiberty-libertyOfString.size();*/
    }

    public String getLibertyString() {
        String s="";
        for (Stone stone : stones) {
            s+=stone.getLiberty();
        }
        return s;
    }

    public String toString() {
        return "StoneString{" +
                "liberty=" + liberty +
                "libertyIndex" +getLibertyString() +
                "count=" + stones.size() +
                '}';
    }
}
