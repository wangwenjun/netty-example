package com.wangwenjun.bio.serversocket;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by wangwenjun on 2016/4/24.
 */
public class FileTransportClient {

    public void startTransport(String filePath)
            throws IOException {
        Socket socket = new Socket("localhost", 9999);
        OutputStream outputStream = socket.getOutputStream();
        File file = new File(filePath);
        sendBasicData(outputStream, file);

        try (FileInputStream fis = new FileInputStream(file)) {
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        }

        System.out.println("文件传输完毕.");
        InputStream inputStream = socket.getInputStream();
        byte[] response = new byte[1];
        inputStream.read(response);
        System.out.println("服务端应答:" + response[0]);
    }

    private void sendBasicData(OutputStream outputStream, File file)
            throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String fileName = file.getName();
        byte[] fileNameBytes = fileName.getBytes(Charset.forName("UTF-8"));
        long fileLength = file.length();

        byteBuffer.putInt(fileNameBytes.length);
        byteBuffer.put(fileNameBytes);
        byteBuffer.putLong(fileLength);
        byteBuffer.flip();
        byte[] data = new byte[byteBuffer.limit()];
        byteBuffer.get(data);
        outputStream.write(data);
    }

    public static void main(String[] args)
            throws IOException {
        final String sourceFile = "D:\\kangaiwang.war";
        new FileTransportClient().startTransport(sourceFile);
    }
}
