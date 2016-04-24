package com.wangwenjun.bio.serversocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wangwenjun on 2016/4/24.
 */
public class ChatDemo {

    public void startChatServer() {

        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket clientSocket = serverSocket.accept();
            BufferedReader reader = createReader(clientSocket.getInputStream());
            PrintWriter writer = createWriter(clientSocket.getOutputStream());
            while (true) {
                String rec = reader.readLine();
                System.out.println("Recv->"+rec);
                if (rec.equals("quit"))
                    break;
                writer.println("Come from server:" + rec);
                writer.flush();
            }

            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader createReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private PrintWriter createWriter(OutputStream outputStream) {
        return new PrintWriter(outputStream);
    }

    public static void main(String[] args) {
        new ChatDemo().startChatServer();
    }
}
