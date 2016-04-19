package com.wangwenjun.bio.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public static void main(String[] args) throws IOException {
        new CloseSocket().finallyClose();
    }
}
