package com.wangwenjun.bio.concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ReplyChatHandler extends ChatHandler {

    public ReplyChatHandler(Socket clientSocket) {
        super(clientSocket);
    }

    @Override
    public void run() {

        try {
            BufferedReader reader = getReader();
            PrintWriter writer = getWriter();
            while (true) {
                String rec = reader.readLine();
                System.out.println("Recv->" + rec);
                if (rec.equals("quit"))
                    break;
                writer.println("Come from server:" + rec);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}