package com.wangwenjun.bio.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wangwenjun on 2016/4/19.
 */
public class CloseSocket {

    /**
     * 使用finally语句块确保关闭
     *
     * @throws IOException
     */
    public void finallyClose() throws IOException {
        Socket socket = null;
        try {
            socket = new Socket("www.94jiankang.com", 80);
            System.out.println(socket.isConnected() ? "连接成功" : "连接失败");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != socket) socket.close();
        }
    }

    /**
     * 使用try...resources确保关闭.
     */
    public void tryResources() {
        try (Socket socket = new Socket("www.94jiankang.com", 80)) {
            System.out.println(socket.isConnected() ? "连接成功" : "连接失败");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 一个简单的半关闭例子.
     */
    public void halfClose() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {

            try {
                ServerSocket serverSocket = new ServerSocket(9999);
                latch.countDown();
                Socket clientSocket = serverSocket.accept();
                InputStream inputStream = clientSocket.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                String clientName = reader.readLine();
                System.out.printf("客户端%s登录成功\n", clientName);
                //关闭了输入流，因为服务端再也不接受消息了.
                clientSocket.shutdownInput();

                while (true) {
                    PrintWriter printWriter = new PrintWriter(
                            clientSocket.getOutputStream());
                    printWriter.println("I am come from server.");
                    printWriter.flush();
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        //等待服务端创建成功.
        latch.await();

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 9999);
                PrintWriter printWriter = new PrintWriter(
                        socket.getOutputStream());
                //模拟登录.
                printWriter.println("Wangwenjun");
                printWriter.flush();

                //客户端关闭了输出流，因为从此之后他不再发送数据了.
                socket.shutdownOutput();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(">>>" + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }




    public static void main(String[] args) throws InterruptedException {
        new CloseSocket().halfClose();
    }
}
