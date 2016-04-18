package com.wangwenjun.bio.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by wangwenjun on 2016/4/18.
 */
public class SocketMethod {

    public void useSocketMethod() throws IOException {
        Socket socket = new Socket("www.94jiankang.com", 80);
        System.out.println("getLocalAddress():" + socket.getLocalAddress());
        System.out.println("getLocalPort():" + socket.getLocalPort());
        System.out.println("getLocalSocketAddress():" + socket.getLocalSocketAddress());
        System.out.println("getPort():" + socket.getPort());
        System.out.println("isBound():" + socket.isBound());
        System.out.println("isClosed():" + socket.isClosed());
        System.out.println("isConnected():" + socket.isConnected());
    }


    public static void main(String[] args) throws IOException {
        new SocketMethod().useSocketMethod();
    }
}
