package com.monki.test;

import com.monki.entity.Position;
import com.monki.entity.Stone;
import com.monki.util.Calculator;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.Time;

public class StoneClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = "localhost"; // 服务器地址
        int port = 12345; // 服务器端口
        int count = 2;//初始为白方
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            while (true) {
                    Stone receivedStone = (Stone) ois.readObject();
                    System.out.println("从服务器接收到的棋子: " + receivedStone);
                    count = receivedStone.getCount() + 1;
                    //发送棋子
                    Stone stone = new Stone(count, Color.BLACK, new Position(4, 4), Calculator.getCoordinateViaIndex(4, 4)); // 创建一个Stone对象
                    oos.writeObject(stone); // 发送Stone对象到服务器
                    oos.flush(); // 确保对象被发送
                    System.out.println("发送了棋子"+stone);
                    Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}