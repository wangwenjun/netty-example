package com.wangwenjun.bio.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by wangwenjun on 2016/4/24.
 */
public class SimpleUdp {

    public void simpleTest() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                DatagramSocket datagramSocket = new DatagramSocket(1999);
                System.out.println("UDP port is open");
                latch.countDown();

                while (true) {
                    DatagramPacket datagramPacket = new DatagramPacket(new byte[512], 512);
                    datagramSocket.receive(datagramPacket);
                    String msg = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                    System.out.println(datagramPacket.getAddress() + ":" + datagramPacket.getPort() + ">" + msg);

                    datagramPacket.setData(("echo " + msg).getBytes());
                    datagramSocket.send(datagramPacket);
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        latch.await();

        new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                String msg = null;
                while ((msg = reader.readLine()) != null) {
                    byte[] data = msg.getBytes();
                    DatagramPacket packet = new DatagramPacket(data, data.length,
                            InetAddress.getByName("localhost"), 1999);
                    socket.send(packet);

                    DatagramPacket recPacket = new DatagramPacket(new byte[512], 512);
                    socket.receive(recPacket);

                    System.out.println(new String(recPacket.getData(), 0, recPacket.getLength()));
                    if (msg.equals("quit"))
                        break;
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }

    public static void main(String[] args) throws InterruptedException {
        new SimpleUdp().simpleTest();
    }
}