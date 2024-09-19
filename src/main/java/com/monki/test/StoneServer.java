package com.monki.test;
import com.monki.entity.Position;
import com.monki.entity.Stone;
import com.monki.util.Calculator;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class StoneServer {
    public static void main(String[] args) throws IOException {
        int port = 12345; // 服务器监听的端口
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            //等待连接建立
            System.out.println("服务器启动，等待连接...");
            Socket clientSocket = serverSocket.accept(); // 等待客户端连接
            System.out.println("客户端已连接");
            //棋子的接收与发送
            int count = 1;//初始为黑方
            try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {
                //服务器，黑方
                while (true) {
                    //发送棋子
                    //Stone stone = new Stone(count, Color.BLACK, new Position(3, 3), Calculator.getCoordinateViaIndex(3, 3));
                    Stone stone =null;
                    oos.writeObject(stone); // 将相同的stone对象发送回客户端
                    oos.flush(); // 确保对象被发送
                    System.out.println("发送了棋子"+stone);
                    //接受棋子
                    stone = (Stone) ois.readObject(); // 读取客户端发送的Stone对象
                    System.out.println("接收到棋子: " + stone);
                    count = stone.getCount() + 1;
                    Thread.sleep(1000);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                clientSocket.close(); // 确保在最后关闭客户端套接字
            }
        }
    }
}