package com.wangwenjun.bio.serversocket;

import com.sun.corba.se.spi.activation.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by wangwenjun on 2016/4/24.
 */
public class ServerSocketMethod {

    public void acceptConnectFromClient() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                //程序进入阻塞模式
                Socket clientSocket = serverSocket.accept();
                //退出阻塞模式，并且成功得到client socket.
                System.out.println("客户端:" + clientSocket.getRemoteSocketAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServerSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket clientSocket = serverSocket.accept();
            //关闭server socket
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void detailsOfServerSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            InetAddress inetAddress = serverSocket.getInetAddress();
            System.out.println("inetAddress->" + inetAddress);

            int localPort = serverSocket.getLocalPort();
            System.out.println("localPort->" + localPort);

            SocketAddress localSocketAddress = serverSocket.getLocalSocketAddress();
            System.out.println("localSocketAddress->" + localSocketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createZeroServerSocket() {
        for (int i = 0; i < 10; i++) {
            try (ServerSocket serverSocket = new ServerSocket(0)) {
                System.out.println("服务端的端口是:"+serverSocket.getLocalPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ServerSocketMethod().createZeroServerSocket();
    }
}
