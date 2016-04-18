package com.wangwenjun.bio.socket;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CreateSocket {

    private final String host = "www.94jiankang.com";

    private final int port = 80;

    private void createSocketUseNoArguments()
            throws UnknownHostException {

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port));
            System.out.println(socket.getLocalAddress());
            System.out.println(socket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSocketUsingLocalAddressAndPort()
            throws UnknownHostException {

        InetAddress localInetAddress = InetAddress.getByName("192.168.88.105");
        int localPort = 7819;

        InetAddress serverInetAddress = InetAddress.getByName(host);
        try (Socket socket = new Socket(serverInetAddress, port, localInetAddress, localPort)) {
            System.out.println(socket.getLocalAddress());
            System.out.println(socket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSocketWithInetAddressAndPort()
            throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(host);
        try (Socket socket = new Socket(inetAddress, port)) {
            System.out.println(socket.getLocalAddress());
            System.out.println(socket.getLocalPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Socket(String host, int port)
     */
    public void createSocketWithHostAndPort() {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("connect the server successful.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        new CreateSocket().createSocketUseNoArguments();
    }
}
