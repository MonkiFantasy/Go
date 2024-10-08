package com.monki.draw;

import com.monki.core.Board;
import com.monki.core.StoneString;
import com.monki.socket.StoneClient;
import com.monki.socket.StoneServer;
import com.monki.util.FileSaver;
import com.monki.util.Calculator;
import com.monki.util.Config;
import com.monki.entity.Position;
import com.monki.entity.Stone;
import com.monki.util.MyLogger;


import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyPanel extends JPanel {
    public static final int X = Config.X;//棋盘左上角顶点x坐标
    public static final int Y = Config.Y;//棋盘左上角顶点y坐标
    public static final int LENGTH = Config.LENGTH;//棋盘宽度
    //public static final int PATH=Config.PATH;//围棋路数
    public static final int SPACE = Config.SPACE;
    public static MyPaint myPaint = new MyPaint();
    public static Position mouseOn = null;//鼠标指针距离最近交叉点坐标
    public static List<Stone> fallOn = new ArrayList<>();//已落子的信息
    public static int turn = -1;//-1黑 1白
    public static int count = 1;//对弈手数
    //Logger logger =Logger.getLogger("panel");
    //private Board board= new Board();
    //private static Stone[][] stones=Board.stones;//存放棋盘上落得子
    private JButton menu;
    private  JButton musicPlayer;
    private JButton saveSGF;
    private JPanel textPanel;
    private static JTextArea text;
    private Clip clip;
    private JFrame myFrame;


    public MyPanel(JFrame frame) {
        myFrame=frame;
        initMusic();
        initPanel();
        initListener();

    }


    @Override
    public void paintComponent(Graphics gh) {
        super.paintComponent(gh);
        Graphics2D g = (Graphics2D) gh;
        //background
        try {
            Image image = ImageIO.read(getClass().getResource("/img/img.png"));
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //棋盘绘制
        myPaint.drawLines(g);
        myPaint.drawIndex(g);
        myPaint.drawStars(g);
        //实现落子提示效果
        if (mouseOn != null) {
            g.setColor(Color.RED);

            g.fillRect(mouseOn.getI() - Config.SPACE / 4, mouseOn.getJ() - Config.SPACE / 4, SPACE/2, SPACE/2);
        }
        //落子实现
        if (!fallOn.isEmpty()) {
            //g.setColor(Color.BLACK);
            //Boolean isBlack = true;
            for (Stone stone : fallOn) {
                if (!stone.getRemoved()) {
                    myPaint.drawStone(g, stone);
                    //落子焦点
                    if(stone.getCount()==(fallOn.size())){
                        int i = stone.getCoordinate().getI();
                        int j = stone.getCoordinate().getJ();
                        //g.setColor(Color.BLUE);
                        g.setColor(stone.getColor().equals(Color.WHITE)?Color.BLACK:Color.WHITE);
                        g.fillPolygon(new int[]{i,i,i+Config.SPACE/2},new int[]{j,j+Config.SPACE/2,j}, 3);
                        //g.fillOval(stone.getCoordinate().getI()-Config.SPACE/8,stone.getCoordinate().getJ()-Config.SPACE/8,SPACE/4,SPACE/4);
                    }
                    //绘制手数
                    //g.setColor(stone.getColor().equals(Color.WHITE)?Color.BLACK:Color.WHITE);
                    //g.drawString(String.valueOf(stone.getCount()), stone.getCoordinate().getI()-Config.SPACE/8,stone.getCoordinate().getJ()+Config.SPACE/8);
                }
            }
        }
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        //TODO:重构当前方法中代码，使逻辑更清晰
        public void mousePressed(MouseEvent e) {

            //鼠标按下落子
            if (Calculator.isInBoard(e.getX(), e.getY())) {
                Position index = Calculator.getIndexViaMouse(e.getX(), e.getY());
                Position coordinate = Calculator.getCoordinateViaIndex(index.getI(), index.getJ());
                Stone stone = new Stone(count, turn == -1 ? Color.BLACK : Color.WHITE, coordinate, index);
                //网络传送棋子
                if(Config.MODE==0){
                    //判断落子是否合法(重复落子，自杀，全局同形）
                    if (isValidStone(index,stone)) {
                        //落子合法，执行落子逻辑
                        updateStone(stone);
                        repaint();
                    }
                }else if(Config.MODE==1){
                    //TODO:实现网络传输
                    //从服务器获取当前方是否为服务器(黑)
                    Boolean isServer=Config.SERVER;
                    //从服务器获取当前方是黑棋还是白棋
                    //int player=1;
                    //获取当前落子方是黑棋还是白棋，是当前方落子:执行落子逻辑updateStone，发送棋子到对方
                    if(isServer){
                        /*if(count==1){
                            new Thread(new StoneServer()).start();
                        }*/
                        if (turn==-1){
                            //是当前方落子
                            if(isValidStone(index,stone)){
                                updateStone(stone);
                                //repaint();
                                StoneServer.currentStone=stone;
                            }

                        }else if(turn==1){
                            //是对方落子,不该当前方落子，弹窗
                            if(isValidStone(index,stone)){
                                //stone=StoneServer.currentStone;
                                //updateStone(stone);
                            }
                        }
                    }else{
                        /*if(count==1){
                            new Thread(new StoneClient()).start();
                        }*/
                        //是客户端，白棋
                        if(turn==1){
                            if(isValidStone(index,stone)){
                                updateStone(stone);
                                StoneClient.currentStone=stone;
                                //notify();
                            }
                        } else if (turn==-1) {
                            //是对方落子,不该当前方落子，弹窗
                            if(isValidStone(index,stone)){
                                //stone=StoneClient.currentStone;
                                //TODO
                                //if(stone==null) return;
                                //updateStone(stone);
                            }
                        }
                    }
                }



                MyLogger.log("count" + count, this.getClass());


                //重绘当前落下棋子区域
                repaint(coordinate.getI() - Config.SPACE / 2, coordinate.getJ() - Config.SPACE / 2, SPACE * 2, SPACE * 2);
                //System.out.println(stone.toString());
                //日志输出Board.state[][]
                showBoardState();
                //日志输出fallon
                showFallon();

            }

        }
    }

    public static void updateStone(Stone stone) {
        Position index = stone.getIndex();
        //先更新棋盘的状态
        stone.checkLiberty();
        Board.stones[index.getJ()][index.getI()] = stone;//1
        Board.state[index.getJ()][index.getI()] = turn;//2
        //落子后更新敌方棋串的气坐标
        if (turn == -1) {
            for (StoneString stoneString : Board.whiteString) {
                stoneString.updateLiberty(index);
            }
        } else if (turn == 1) {
            for (StoneString stoneString : Board.blackString) {
                stoneString.updateLiberty(index);
            }
        }
        //更新对方棋串的气，若为0则提掉
        removeOppositeDeathString(index);
        //重新判断当前棋子的气
        stone.checkLiberty();
        Board.stones[index.getJ()][index.getI()] = stone;
        //连接棋串
        Board.connectString(stone, turn);
        Board.history.add(Calculator.deepCopy(Board.state));
        //repaint();
        text.setText("请"+(turn==-1?"白":"黑")+"方落子 当前手数："+count);
        //轮次更新
        turn = -turn;//更新当前棋手
        count++;//落子手数增加
    }

    private static void removeOppositeDeathString(Position index) {
        //提子逻辑
        //获取要被移除的敌方棋串
        //TODO：修复getStonesToRemove方法，貌似因为落子后未更新被堵住的气,里面的isOppositeStringDeath判断为false,
        // 导致不执行下面的if逻辑,执行这个逻辑前要先落子重新计算Board.stones棋子的气，和其中棋串的气
        List<StoneString> stoneStrings = Board.getStonesToRemove(index, turn);
        if (!stoneStrings.isEmpty()) {
            //找到被提的棋串，设置其中每个棋子isRemoved属性为true，删除棋串
            stoneStrings.forEach(stoneString -> {
                Set<Stone> stones = stoneString.getStones();
                stones.forEach(s -> {
                    //这个s是Board里stones[][]里获取当前位置stone的stoneString里面的所有stone,是个复制
                    //要设置fallon里的isRemoved属性为true,先设置当前stone的属性，再根据索引重置fallon中的
                    s.setRemoved(true);
                    fallOn.set(s.getCount() - 1, s);
                    System.out.println(s);
                    //TODO:删除被提的棋串,设置棋盘state[][]为0,stones[][]为null
                    Board.state[s.getIndex().getJ()][s.getIndex().getI()] = 0;
                    Board.stones[s.getIndex().getJ()][s.getIndex().getI()] = null;
                });
                //从棋盘上删除棋串
                if (turn == -1) {
                    Board.blackString.remove(stoneString);
                } else if (turn == 1) {
                    Board.whiteString.remove(stoneString);
                }
            });
        }
    }

    private void showFallon() {
        StringBuilder msg = new StringBuilder();
        for (Stone stone1 : fallOn) {
            msg.append(stone1);
        }
        MyLogger.log(msg.toString(), this.getClass());
    }

    private void showBoardState() {
        MyLogger.log("Board.state[][]", this.getClass());
        StringBuilder boardState = new StringBuilder();
        for (int i = 1; i <= 19; i++) {
            for (int j = 1; j <= 19; j++) {
                boardState.append(Board.state[i][j]).append(" ");
                //System.out.println(Board.stones[i][j]);
            }
            boardState.append("\n");
        }
        MyLogger.log(boardState.toString(), this.getClass());
    }

    private boolean isValidStone(Position index,Stone stone) {
        //TODO:实现判断落子合法性
        //当前落子颜色与轮次颜色不同
        if(Config.MODE==1&&((stone.getColor().equals(Color.WHITE)&&Config.SERVER)||(stone.getColor().equals(Color.BLACK)&&!Config.SERVER))){
            MyLogger.log("不是你的回合", this.getClass());
            new WarningDialog("不是你的回合，请等待对方落子");
            return false;
        }
        //当前坐标已落子
        if (Board.state[index.getJ()][index.getI()] != 0) {
            MyLogger.log("当前坐标已落子，请到别处落子", this.getClass());
            new WarningDialog("当前坐标已落子，请到别处落子");
            return false;
        }
       //TODO:判断是否自杀
        if (isSuicide(index, turn,stone)) {
            MyLogger.log("你不能自杀", this.getClass());

            new WarningDialog("你不能自杀，请到别处落子");
            return false;
        }
        //TODO:判断是否全局同形
        if (isAppeared(index)) {
            MyLogger.log("违反了禁全局同形规则，请在别处落子", this.getClass());
            new WarningDialog("违反了禁全局同形规则，\n请到别处落子");
            return false;
        }

        return true;
    }

    private boolean isAppeared(Position index) {
        //int[][] clone = Board.state.clone();//这里是引用，不能clone
        int[][] clone = Calculator.deepCopy(Board.state);
        int i = index.getJ(); // 假设这里的J是行，I是列（通常行列是反的，但这里按您的代码处理）
        int j = index.getI();
        //真实棋盘落子（后面恢复）
        Board.state[i][j] = turn;//2
        //模拟棋盘落子
        clone[i][j]=turn;
        //落子后更新敌方棋串的气坐标
        if (turn == -1) {
            for (StoneString stoneString : Board.whiteString) {
                stoneString.updateLiberty(index);
            }
        } else if (turn == 1) {
            for (StoneString stoneString : Board.blackString) {
                stoneString.updateLiberty(index);
            }
        }
        //落子后模拟提子
        List<StoneString> stoneStrings = Board.getStonesToRemove(index, turn);
        if (!stoneStrings.isEmpty()) {
            //找到被提的棋串，设置其中每个棋子isRemoved属性为true，删除棋串
            stoneStrings.forEach(stoneString -> {
                Set<Stone> stones = stoneString.getStones();
                stones.forEach(s -> {
                    //设置模拟棋盘中被提掉的棋子
                    clone[s.getIndex().getJ()][s.getIndex().getI()] = 0;
                });
            });
        }
        //恢复真实棋盘状态
        Board.state[i][j] = 0;
        for (int[][] ints : Board.history) {
            if(Calculator.areArraysEqual(clone,ints)){
                return true;
            }
        }
        return false;
    }

    //TODO：修改自杀逻辑，先判断是否能提子，如果能则不算自杀，判断落子后当前棋串的气是否为0，为0则算自杀
    private boolean isSuicide(Position index, int player,Stone stone) {
        int[][] clone = Calculator.deepCopy(Board.state);
        int i = index.getJ(); // 假设这里的J是行，I是列（通常行列是反的，但这里按您的代码处理）
        int j = index.getI();
        Position up=new Position(i-1,j);
        Position down=new Position(i+1,j);
        Position left=new Position(i,j-1);
        Position right=new Position(i,j+1);
        //真实棋盘落子（后面恢复）
        Board.state[i][j] = turn;//2
        //模拟棋盘落子
        clone[i][j]=turn;
        //落子后更新敌方棋串的气坐标
        if (turn == -1) {
            for (StoneString stoneString : Board.whiteString) {
                stoneString.updateLiberty(index);
            }
        } else if (turn == 1) {
            for (StoneString stoneString : Board.blackString) {
                stoneString.updateLiberty(index);
            }
        }
        //落子后模拟提子
        List<StoneString> stoneStrings = Board.getStonesToRemove(index, turn);
        //恢复棋盘状态
        Board.state[i][j] = 0;
        if (!stoneStrings.isEmpty()) {
            //能提子，不算自杀
            return false;
        }else{
            //TODO：连接棋串，判断上下左右是否有当前方棋串，没有就把当前棋子作为一个子串，存入当前方StoneString的list
            StoneString mergedString = new StoneString();
            if(Board.existStone(up,player)){
                StoneString u = Board.getStoneStringByIndex(up);
                mergedString.addStone(stone);
                mergedString.addStones(u.getStones());
                //boolean b = player == -1 ? blackString.remove(u) : whiteString.remove(u);
            }
            if (Board.existStone(down,player)) {
                StoneString d = Board.getStoneStringByIndex(down);
                mergedString.addStone(stone);
                mergedString.addStones(d.getStones());
                //boolean b = player == -1 ? whiteString.remove(d) : blackString.remove(d);
            }
            if (Board.existStone(left,player)) {
                StoneString l = Board.getStoneStringByIndex(left);
                mergedString.addStone(stone);
                mergedString.addStones(l.getStones());
                //boolean b = player == -1 ? whiteString.remove(l) : blackString.remove(l);
            }
            if (Board.existStone(right,player)) {
                StoneString r = Board.getStoneStringByIndex(right);
                mergedString.addStone(stone);
                mergedString.addStones(r.getStones());
                //boolean b = player == -1 ? whiteString.remove(r) : blackString.remove(r);
            }
            mergedString.addStone(stone);
            if(mergedString.getLiberty()==0){
                return true;
            }


            //更新当前stone所在的棋串
            //stones[i][j].setMyString(mergedString);
            //MyPanel.fallOn.add(stone);
            //todo:更新当前串前面棋子所在的串
            /*Set<Stone> stonesOfString = mergedString.getStones();
            for (Stone stoneOfString : stonesOfString) {
                stoneOfString.setMyString(mergedString);
                MyPanel.fallOn.set(stoneOfString.getCount()-1,stoneOfString);
            }*/
            //MyPanel.fallOn
            //存入对应list
            /*if (player==-1){
                blackString.add(mergedString);
            } else if (player==1) {
                whiteString.add(mergedString);
            }  */

        }

        /*int count = 0; // 初始化为0，因为需要根据实际在棋盘上的方向来累加
        int i = index.getJ(); // 假设这里的J是行，I是列（通常行列是反的，但这里按您的代码处理）
        int j = index.getI();
        // 检查上方
        if (Board.isOnBoard(new Position(i - 1, j))) {
            count++; // 在棋盘上，先加一
            if (Board.existStone(new Position(i - 1, j), -player)) {
                count--; // 如果有敌方棋子，再减一
            }
        }
        // 检查下方
        if (Board.isOnBoard(new Position(i + 1, j))) {
            count++;
            if (Board.existStone(new Position(i + 1, j), -player)) {
                count--;
            }
        }
        // 检查左方
        if (Board.isOnBoard(new Position(i, j - 1))) {
            count++;
            if (Board.existStone(new Position(i, j - 1), -player)) {
                count--;
            }
        }
        // 检查右方
        if (Board.isOnBoard(new Position(i, j + 1))) {
            count++;
            if (Board.existStone(new Position(i, j + 1), -player)) {
                count--;
            }
        }
        return count == 0;*/

        // 如果气数为0，则为自杀
       /* int count=4;//当前棋子存在的气数
        //如果上下左右均为敌方棋子，则为自杀?
        //图形学坐标转换为数组坐标
        int i = index.getJ();
        int j = index.getI();
        Position up=new Position(i-1,j);
        Position down=new Position(i+1,j);
        Position left=new Position(i,j-1);
        Position right=new Position(i,j+1);
        //棋子在棋盘角，边界，只判断、边界内有的棋子
        if(!Board.isOnBoard(up)){
            count--;
        }else if(!Board.isOnBoard(down)){
            count--;
        }else if (!Board.isOnBoard(left)){
            count--;
        }else if (!Board.isOnBoard(right)){
            count--;
        }
        //前面判断当前棋子的位置，剩余气count=2在角上，3在边上，4在棋盘内
       *//* if (count == 2) {*//*

        //有棋子堵住气，气为零则死
        if(Board.existStone(up, -player)) count--;
        if(Board.existStone(down,-player)) count--;
        if(Board.existStone(left,-player)) count--;
        if(Board.existStone(right,-player)) count--;
        return count==0;*/
        /**/
        return false;
    }
        /*if (count == 3) {
            //判断上下左右是否有棋子
            return !Board.existStone(up, -player) && !Board.existStone(down, -player) && !Board.existStone(left, -player) && !Board.existStone(right, -player);
        }*/
        /*if(count==4) {
            return Board.existStone(up, -player) && Board.existStone(down, -player) && Board.existStone(left, -player) && Board.existStone(right, -player);
        }*/


    private void initPanel() {
        setLayout(null);
        setBounds(0, 0, 1920, 1080);
        setBackground(Color.gray);
        menu = new MyButton("主菜单");
        menu.setBounds(X+LENGTH + SPACE, Y , SPACE * 5, (int) (SPACE*1.2));
        textPanel = new BackgroundPanel("/img/img_1.png");
        textPanel.setToolTipText("15351");
        //textPanel = new JPanel();
        //textPanel.setOpaque(true);
        textPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        textPanel.setBounds(X + LENGTH+SPACE, Y+SPACE*4, SPACE * 10, SPACE*12);
        text = new JTextArea();
        //text.setBounds(X + LENGTH+SPACE, Y+SPACE*4, SPACE * 10, SPACE*12);
        //透明背景
        text.setBackground(new Color(0, 0, 0, 0));
        //text.setOpaque(true);
        text.setText("执黑先行");
        text.setFont(new Font("宋体", Font.BOLD, 20));
        musicPlayer = new MyButton("背景音乐：关");
        musicPlayer.setBounds(X + SPACE +LENGTH, Y+SPACE*2, SPACE * 5, (int) (SPACE*1.2));
        saveSGF = new MyButton("保存棋谱");
        saveSGF.setBounds(X + SPACE +LENGTH+SPACE*6, Y, SPACE * 5, (int)(SPACE*1.2));
        setDoubleBuffered(true);
        add(menu);
        add(musicPlayer);
        add(saveSGF);
        textPanel.add(text);
        add(textPanel);
        setVisible(true);
    }
    public void initMusic(){
        try {
            System.out.println("当前工作目录：" + System.getProperty("user.dir"));
            AudioInputStream bgm = AudioSystem.getAudioInputStream(new BufferedInputStream(this.getClass().getResourceAsStream("/music/Go.wav")));
            clip = AudioSystem.getClip();
            clip.open(bgm);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    private void initListener() {

        //鼠标点击监听（落子监听）
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                //鼠标移动落子位置高亮提示
                if (Calculator.isInBoard(e.getX(), e.getY())) {
                    mouseOn = Calculator.getIndexViaMouse(e.getX(), e.getY());
                    mouseOn = Calculator.getCoordinateViaIndex(mouseOn.getI(), mouseOn.getJ());
                    repaint();
                }
            }
        });
        //弹出连接对话框
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.remove(MyFrame.myPanel);
                myFrame.setBounds(710,290,500,500);
                myFrame.add(MyFrame.startPanel);
            }
        });
        musicPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source =(JButton) e.getSource();
                if (source.getText().equals("背景音乐：开")) {
                    clip.stop();
                    source.setToolTipText("点击播放");
                    source.setText("背景音乐：关");
                }else if (source.getText().equals("背景音乐：关")){
                    clip.start();
                    source.setText("背景音乐：开");
                    source.setToolTipText("点击暂停");
                }
            }
        });
        saveSGF.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                sb.append("(;GM[1]FF[4]CA[UTF-8]SZ[19];\n");
                for (Stone stone : fallOn) {
                    if (stone.getColor().equals(Color.BLACK)) {
                        sb.append("B[");
                        //sb.append(Calculator.getCoordinateViaIndex(stone.getIndex().getI(), stone.getIndex().getJ()));
                        sb.append(Calculator.getAlphaIndex(stone.getIndex()));
                        sb.append("]");
                    } else if (stone.getColor().equals(Color.WHITE)) {
                        sb.append("W[");
                        sb.append(Calculator.getAlphaIndex(stone.getIndex()));
                        sb.append("]");
                        //sb.append(Calculator.getCoordinateViaIndex(stone.getIndex().getI(), stone.getIndex().getJ()));
                    }
                    if(stone.getCount()!=fallOn.size()){
                       sb.append(";");
                    }
                }
                sb.append(")");
                try {
                    FileSaver.save(sb.toString());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }
}
