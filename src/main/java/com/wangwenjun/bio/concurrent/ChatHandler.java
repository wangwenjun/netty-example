package com.wangwenjun.bio.concurrent;

import java.io.*;
import java.net.Socket;

/**
 * Created by wangwenjun on 2016/4/24.
 */
public abstract class ChatHandler implements Runnable {

    private final Socket clientSocket;

    public ChatHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    protected BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    protected PrintWriter getWriter() throws IOException {
        return new PrintWriter(clientSocket.getOutputStream());
    }
}
