package io.netty.example.chapter2;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by wangwenjun on 2016/4/3.
 */
public class EchoClient {
    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 10; i++) {
            Socket socket = new Socket("localhost", 1029);
            new Thread(() -> {
                try {
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(("Hello " + Thread.currentThread().getName()).getBytes(Charset.forName("UTF-8")));
                    outputStream.flush();
                    byte[] buffer = new byte[16];
                    int len;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while((len=inputStream.read(buffer))!=-1)
                    {
                        baos.write(buffer,0,len);
                    }

                    System.out.println(new String(baos.toByteArray(),Charset.forName("UTF-8")));

                    Thread.sleep(1000*120);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
