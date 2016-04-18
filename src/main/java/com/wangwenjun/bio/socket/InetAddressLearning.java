package com.wangwenjun.bio.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetAddressLearning {

    private final byte[] ipBytes = {0x78, 0x18, 0xfffffff5, 0xffffffe5};

    public void createInetAddress() throws UnknownHostException {

        //根据域名获取InetAddress
        InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddress);

        //根据Ip地址的byte[]数组获取InetAddress
        inetAddress = InetAddress.getByAddress(new byte[]{0x78, 0x18, 0xfffffff5, 0xffffffe5});
        System.out.println(inetAddress);

        //根据域名获取相关的地址数组.
        InetAddress[] inetAddresses = InetAddress.getAllByName("www.baidu.com");
        Arrays.stream(inetAddresses).forEach(System.out::println);

        //获取本地的InetAddress
        InetAddress localInetAddress = InetAddress.getLocalHost();
        System.out.println(localInetAddress);


        //获取回环地址
        InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
        System.out.println(loopbackAddress);
    }

    public void useInetAddress() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("www.94jiankang.com");

        //获取InetAddress的byte数组
        byte[] ipBytes=inetAddress.getAddress();
        String canonicalHostName = inetAddress.getCanonicalHostName();
        System.out.println("canonicalHostName:"+canonicalHostName);

        String hostName = inetAddress.getHostName();
        System.out.println("hostName:"+hostName);

        String hostAddress = inetAddress.getHostAddress();
        System.out.println("hostAddress:"+hostAddress);
    }

    public static void main(String[] args) throws UnknownHostException {
        new InetAddressLearning().useInetAddress();
    }
}
