package com.wangwenjun.bio.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wangwenjun on 2016/4/23.
 */
public class SocketParameters {


    public void testTcpNoDelay() {

        try (Socket socket = new Socket("www.94jiankang.com", 80)) {
            System.out.println("TcpNoDelay->" + socket.getTcpNoDelay());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testTimeout() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(1999);
                latch.countDown();
                Socket clientSocket = serverSocket.accept();
                System.out.println("接到了来自客户端[" + clientSocket + "]的连接.");
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        latch.await();

        new Thread(() -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("localhost", 1999));
                byte[] buffer = new byte[1024];
                InputStream inputStream = socket.getInputStream();
                int len;
                System.out.println("默认的读取超时时间是:"+socket.getSoTimeout());
                socket.setSoTimeout(1000);
                System.out.println("设置后的读取超时时间是:"+socket.getSoTimeout());
                while ((len = inputStream.read(buffer)) != -1) {
                    System.out.printf("读取了%d个长度的数据\n", len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void testResUseAddr() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(1999);
                latch.countDown();
                Socket clientSocket = serverSocket.accept();
                OutputStream outputStream = clientSocket.getOutputStream();
                while (true) {
                    outputStream.write(new byte[1024 * 1024]);
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        latch.await();

        new Thread(() -> {
            try {
                Socket socket = new Socket();
                socket.setSoLinger(true,0);
                socket.setReuseAddress(false);
                socket.connect(new InetSocketAddress("localhost", 1999));
                socket.getInputStream().read(); //读取一个
                long closeStart = System.currentTimeMillis();
                socket.close();
                System.out.printf("关闭用了%dms\n", (System.currentTimeMillis() - closeStart));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        new SocketParameters().testTimeout();
    }
}
