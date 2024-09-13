package com.monki.test;

import com.monki.util.Position;
import com.monki.util.Stone;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class socketClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 8888);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(new Stone(1,Color.BLACK, new Position(4,4)));
        socket.shutdownOutput();

        /*InputStream inputStream = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Stone stone = (Stone) ois.readObject();
*/
    }
}
