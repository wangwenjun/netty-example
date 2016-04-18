package com.wangwenjun.bio.socket;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wangwenjun on 2016/4/17.
 */
public class SocketException {

    public void raiseUnknownHostException() {
        final String address = "unknownHost";
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, 80));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void raiseConnectException() {
        try (Socket socket = new Socket("www.94jiankang.com", 1234)) {
            System.out.println(socket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void raiseConnectException2() throws InterruptedException {
        final int port = 1234;
        //允许的最大连接数.
        final int ALLOW_MAX_CONNECT = 2;
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port, ALLOW_MAX_CONNECT)) {
                latch.countDown();
                Socket clientSocket = serverSocket.accept();
                System.out.println("收到了来自客户端[" + clientSocket.getInetAddress() + "]的连接.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        //上面的代码模拟创建了一个服务器监听程序.

        //等待服务器端创建完成.
        latch.await();

        for (int i = 0; i < 3; i++) {
            try {
                Socket socket = new Socket("localhost", port);
                System.out.println("链接服务端成功.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void raiseTimeoutException() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(
                            InetAddress.getByName("www.94jiankang.com"), 80)
                    , 1
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void raiseBindException() throws UnknownHostException {
        int localPort = 1718;
        InetAddress localInetAddress = InetAddress.getByName("192.168.88.105");
        InetAddress remoteInetAddress = InetAddress.getByName("www.94jiankang.com");
        for (int i = 0; i < 2; i++) {
            try {
                Socket socket = new Socket(remoteInetAddress, 80,
                        localInetAddress,localPort);
                System.out.println("链接创建成功.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        new SocketException().raiseBindException();
    }
}
