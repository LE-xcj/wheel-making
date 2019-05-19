package com.xc.server;

import com.xc.initializer.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author chujian
 * @ClassName HeartBeatDetectServer
 * @Description 功能描述
 * @date 2019/5/17 23:11
 */
public class HeartBeatDetectServer {

    private static HeartBeatDetectServer heartBeatDetectServer;

    static {
        heartBeatDetectServer = new HeartBeatDetectServer();
    }

    private HeartBeatDetectServer() { }

    public static void create() {
        init();
    }

    private static void init() {

        // 连接线程组
        EventLoopGroup connection = new NioEventLoopGroup();

        // io线程组
        EventLoopGroup io = new NioEventLoopGroup();

        // 服务端
        ServerBootstrap server = new ServerBootstrap();

        server.group(connection, io)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_SNDBUF, 1024)
                .option(ChannelOption.SO_RCVBUF, 1024)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ServerInitializer());

        try {

            // 绑定端口
            ChannelFuture future = server.bind("127.0.0.1", 10086).sync();

            System.out.println("server start.....");

            // 关闭
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {

            e.printStackTrace();

        } finally {

            connection.shutdownGracefully();
            io.shutdownGracefully();
        }

    }

}
    