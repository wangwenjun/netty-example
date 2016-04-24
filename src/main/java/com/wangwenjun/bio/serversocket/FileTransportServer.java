package com.wangwenjun.bio.serversocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class FileTransportServer {

    private final static String CHAR_SET = "UTF-8";

    private final static int PORT = 9999;

    private final static String REC_FILE_PATH = "G:/rec";

    public void startServer() {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientSocket = serverSocket.accept();
            InputStream inputStream = clientSocket.getInputStream();
            int len = getFileNameLen(inputStream);

            String fileName = getFileName(inputStream, len);
            long fileLen = getFileLen(inputStream);
            System.out.println("文件名为:" + fileName);
            storeFile(fileName, fileLen, inputStream);
            System.out.println("文件:" + fileName+"保存完毕.");
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(new byte[]{0x00});
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void storeFile(String fileName, long fileLen, InputStream inputStream) {
        try (FileOutputStream fos = new FileOutputStream(
                new File(REC_FILE_PATH, fileName))) {
            final int MAX_LEN = 1024;
            byte[] buffer;
            if (fileLen <= MAX_LEN) {
                buffer = new byte[(int) fileLen];
                inputStream.read(buffer);
                fos.write(buffer);
                fos.flush();
            } else {
                int position = 0;
                buffer = new byte[MAX_LEN];
                int len;
                while ((position < fileLen)&&((len = inputStream.read(buffer)) != -1)) {
                    position += len;
                    fos.write(buffer, 0, len);
                }
                fos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileName(InputStream inputStream, int len) throws IOException {

        byte[] buffer = new byte[len];
        inputStream.read(buffer);

        return new String(buffer, CHAR_SET);
    }

    private int getFileNameLen(InputStream inputStream) throws IOException {
        byte[] fileLenBytes = new byte[4];
        inputStream.read(fileLenBytes);
        return ByteBuffer.wrap(fileLenBytes).asIntBuffer().get();
    }

    private long getFileLen(InputStream inputStream) throws IOException {
        byte[] fileLenBytes = new byte[8];
        inputStream.read(fileLenBytes);
        return ByteBuffer.wrap(fileLenBytes).asLongBuffer().get();
    }

    public static void main(String[] args) {
        new FileTransportServer().startServer();
    }
}