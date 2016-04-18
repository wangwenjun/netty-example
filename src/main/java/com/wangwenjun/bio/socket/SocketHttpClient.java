package com.wangwenjun.bio.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by wangwenjun on 2016/4/17.
 */
public class SocketHttpClient {

    private final String CR = "\r\n";

    private Socket connectServer(String address, int port)
            throws IOException {
        return new Socket(address, port);
    }


    private void readResponse(InputStream inputStream) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }

            System.out.println(new String(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createRequestHeader() {
        StringBuilder builder = new StringBuilder();
        builder.append("GET ").append("/").append(" HTTP/1.1").append(CR);
        builder.append("Host:").append("www.94jiankang.com").append(CR);
        builder.append("Accept:").append("*/*").append(CR);
        builder.append("Accept-Language:").append("zh-cn").append(CR);
        builder.append("Accept-Encoding:").append("gzip,deflate").append(CR);
        builder.append("User-Agent:").append("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36").append(CR);
        builder.append("Connection:").append("Keep-Alive").append(CR).append(CR);

        return builder.toString();
    }

    public void send(String address, int port) {
        try (Socket socket = connectServer(address, port);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {

            String request = createRequestHeader();
            outputStream.write(request.getBytes(Charset.forName("UTF-8")));
            System.out.println(">>>发送请求[" + address + "]成功");
            readResponse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String address = "www.94jiankang.com";
        final int port = 80;
        new SocketHttpClient().send(address, port);
    }

}
