package io.netty.example.chapter2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Created by wangwenjun on 2016/4/4.
 */
public class NettyEchoClient {

    private final int port;

    private final String ip;

    public NettyEchoClient(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyEchoClient(1029, "localhost").start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(ip, port)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });

        ChannelFuture channelFuture = bootstrap.connect().sync();
        channelFuture.isSuccess();
//        channelFuture.channel().closeFuture().sync();
//        System.out.println(">>>>>close done.");
//        loopGroup.shutdownGracefully();
        Thread.currentThread().join();
    }
}
