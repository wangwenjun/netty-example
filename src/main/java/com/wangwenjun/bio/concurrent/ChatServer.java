package com.wangwenjun.bio.concurrent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wangwenjun on 2016/4/24.
 */
public class ChatServer {

    private final int port = 9999;

    public void startChatServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动成功.");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("来自客户端>" + clientSocket.getRemoteSocketAddress());
                new Thread(new ReplyChatHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatServer().startChatServer();
    }
}
