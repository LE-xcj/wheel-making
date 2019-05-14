package com.xc.server;

import com.xc.initializer.ServerInitializer;
import com.xc.utils.ChatChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * @author chujian
 * @ClassName Server
 * @Description 功能描述
 * @date 2019/5/14 17:31
 */
public class ChatServer {

    private static Object lock = new Object();

    private static ChatServer chatServer;

    private static EventLoopGroup connection;
    private static EventLoopGroup io;
    private static ServerBootstrap server;

    private ChatServer() {
        init();
        run();
    }

    /**
     * 初始化服务器的一些配置信息
     */
    private void init() {

        connection = new NioEventLoopGroup();
        io = new NioEventLoopGroup();
        server = new ServerBootstrap();

        server.group(connection, io)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_SNDBUF, 2 * 1024)
                .option(ChannelOption.SO_RCVBUF, 2 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_BACKLOG, 1024)   // TCP缓冲区
                .childHandler(new ServerInitializer());

    }


    private void run() {

        try {
            if (server == null) {
                throw new Exception("server is null");
            }

            System.out.println("server start.....");
            //监听端口的连接， 同步等待绑定成功
            ChannelFuture future = server.bind(10086).sync();

            // 等待服务端监听端口关闭
            future.channel().closeFuture().sync();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            //释放线程组
            connection.shutdownGracefully();
            io.shutdownGracefully();
        }

    }

    /**
     * 运行聊天服务器
     * 单例运行
     */
    public static void work() {

        //double check
        if (null == chatServer) {

            synchronized (lock) {

                if (null == chatServer) {
                    chatServer = new ChatServer();
                }

            }
        }

    }
}
    