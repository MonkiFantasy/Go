package com.monki.draw;

import com.monki.core.Board;
import com.monki.core.StoneString;
import com.monki.util.Calculator;
import com.monki.util.Config;
import com.monki.entity.Position;
import com.monki.entity.Stone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyPanel extends JPanel {
    public static final int X= Config.X;//棋盘左上角顶点x坐标
    public static final int Y=Config.Y;//棋盘左上角顶点y坐标
    public static final int LENGTH=Config.LENGTH;//棋盘宽度
    //public static final int PATH=Config.PATH;//围棋路数
    public static final int SPACE=Config.SPACE;
    public static MyPaint myPaint = new MyPaint();
    public static Position mouseOn=null;//鼠标指针距离最近交叉点坐标
    public static List<Stone> fallOn=new ArrayList<>();//已落子的信息
    public static int turn=-1;//-1黑 1白
    public static int count=1;//对弈手数
   
    
    //private Board board= new Board();
    //private static Stone[][] stones=Board.stones;//存放棋盘上落得子
    private JButton dialog;


    public MyPanel(){
        initPanel();
        initListener();
    }

    private void initListener() {
        //鼠标点击监听（落子监听）
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //鼠标移动落子位置高亮提示
                if(Calculator.isInBoard(e.getX(),e.getY())){
                    mouseOn= Calculator.getIndexViaMouse(e.getX(),e.getY());
                    mouseOn=Calculator.getCoordinateViaIndex(mouseOn.getI(),mouseOn.getJ());
                    repaint();
                    //repaint(mouseOn.getI()- Config.SPACE/2,mouseOn.getJ()-Config.SPACE/2,SPACE,SPACE);
                }
            }
        });
        //弹出连接对话框
        dialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConnectDialog();
            }
        });
    }


    @Override
    public void paintComponent(Graphics gh) {
        super.paintComponent(gh);
        Graphics2D g = (Graphics2D) gh;
        //棋盘绘制
        myPaint.drawLines(g);
        myPaint.drawIndex(g);
        myPaint.drawStars(g);
        //实现落子高亮效果
        if(mouseOn!=null){
            g.setColor(Color.RED);
            g.drawRect(mouseOn.getI()- Config.SPACE/2,mouseOn.getJ()-Config.SPACE/2,SPACE,SPACE);
        }
        //落子实现
        if(!fallOn.isEmpty()){
            g.setColor(Color.BLACK);
            Boolean isBlack = true;
            for (Stone stone : fallOn) {


                if(!stone.getRemoved()) {
                    myPaint.drawStone(g, stone);
                }
            }
        }
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {

            //鼠标按下落子
            if(Calculator.isInBoard(e.getX(),e.getY())){
                Position index = Calculator.getIndexViaMouse(e.getX(),e.getY());
                Position coordinate = Calculator.getCoordinateViaIndex(index.getI(),index.getJ());
                Stone stone = new Stone(count, turn == -1 ? Color.BLACK : Color.WHITE, coordinate,index);


                if(isValidStone(index)) {
                 /*   //TODO：TEST：先落子
                    //提子后判断当前棋子的气
                    stone.checkLiberty();
                    //fallOn.add(stone);
                    //数组的坐标与棋盘图像绘制的坐标相反
                    Board.stones[index.getJ()][index.getI()]=stone;
                    //连接棋串
                    Board.connectString(stone,turn);
                    Board.state[index.getJ()][index.getI()]=turn;
                    //TODO：TEST：先落子*/

                    //提子逻辑
                    //获取要被移除的敌方棋串
                    //TODO：修复getStonesToRemove方法，貌似因为落子后未更新被堵住的气,里面的isOppositeStringDeath判断为false,
                    // 导致不执行下面的if逻辑,执行这个逻辑前要先落子重新计算Board.stones的气
                    List<StoneString> stoneStrings = Board.getStonesToRemove(index, turn);
                    if(stoneStrings!=null){

                        //找到被提的棋串，设置其中每个棋子isRemoved属性为true，删除棋串
                        stoneStrings.forEach(stoneString->{
                            Set<Stone> stones = stoneString.getStones();
                            stones.forEach(s->{
                                //这个s是Board里stones[][]里获取当前位置stone的stoneString里面的所有stone,是个复制
                                //要设置fallon里的isRemoved属性为true,先设置当前stone的属性，再根据索引重置fallon中的
                                s.setRemoved(true);
                                fallOn.set(s.getCount()-1,s);
                                System.out.println(s);
                                //TODO:删除被提的棋串,设置棋盘state[][]为0,stones[][]为null
                                Board.state[index.getJ()][index.getI()]=0;
                                Board.stones[index.getI()][index.getJ()]=null;
                            } );
                            if(turn==-1){
                                Board.blackString.remove(stoneString);
                            }else if(turn==1){
                                Board.whiteString.remove(stoneString);
                            }
                        });
                    }
                    //提子后判断当前棋子的气
                    stone.checkLiberty();
                    fallOn.add(stone);
                    //数组的坐标与棋盘图像绘制的坐标相反
                    Board.stones[index.getJ()][index.getI()]=stone;
                    //连接棋串
                    Board.connectString(stone,turn);
                    Board.state[index.getJ()][index.getI()]=turn;
                    //落子后更新敌方棋串的气坐标
                    if(turn==-1){
                        for (StoneString stoneString : Board.whiteString) {
                            stoneString.updateLiberty(index);
                        }
                    }else if (turn==1){
                        for (StoneString stoneString : Board.blackString) {
                            stoneString.updateLiberty(index);
                        }
                    }
                    //TODO：添加当前棋盘状态到history

                    turn=-turn;//更新当前棋手
                    count++;//落子手数增加
                }
                System.out.println("count:"+count);

                /*if(count==9) {
                    Stone stone1 = fallOn.get(count-1);//List索引从0开始,而count索引从1开始
                    System.out.println(stone1);
                }*/

                //只重绘棋子附近的方形，以提高性能
                repaint(coordinate.getI()-Config.SPACE/2,coordinate.getJ()-Config.SPACE/2,SPACE*2,SPACE*2);
                System.out.println(stone.toString());
                for (int i = 1; i <= 19; i++) {
                    for (int j = 1; j <= 19; j++) {
                        System.out.print(Board.state[i][j]+" ");
                        //System.out.println(Board.stones[i][j]);
                    }
                    System.out.println();
                }
                System.out.println("----------fallon---------------------");
                for (Stone stone1 : fallOn) {
                    System.out.println(stone1);
                }
                System.out.println("----------fallon---------------------");
            }

        }
    }

    private boolean isValidStone(Position index) {
        //TODO:实现判断落子合法性
        if(Board.state[index.getJ()][index.getI()]!=0){
            System.out.println("当前坐标已落子");
            return false;
        }
        //判断是否自杀

        //判断是否全局同形

        return true;
    }

    private void initPanel() {
        setLayout(null);
        setBounds(0,0,1920,1080);
        setBackground(Color.gray);
        dialog = new JButton("进入连接界面");
        dialog.setFont(new Font("宋体", Font.PLAIN, 20));
        dialog.setBounds(X,Y+LENGTH+SPACE,SPACE*4,SPACE);
        setDoubleBuffered(true);
        add(dialog);
        setVisible(true);
    }
}
