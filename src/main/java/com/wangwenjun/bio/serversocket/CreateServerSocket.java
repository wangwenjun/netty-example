package com.wangwenjun.bio.serversocket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/**
 * Created by wangwenjun on 2016/4/24.
 */
public class CreateServerSocket {

    public void createNoArgumentsServerSocket() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(9999));
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createBindPortServerSocket() {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createBindPortAndBacklogServerSocket() {
        try (ServerSocket serverSocket = new ServerSocket(9999, 3)) {
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createBindUniqueIpServerSocket()
            throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("192.168.88.104");
        try (ServerSocket serverSocket = new ServerSocket(9999, 3, inetAddress)) {
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        new CreateServerSocket().createBindUniqueIpServerSocket();
    }
}
