package com.xc.client;

import com.xc.initializer.ClientInitializer;
import com.xc.pojo.ChatProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

/**
 * @author chujian
 * @ClassName Client
 * @Description 功能描述
 * @date 2019/5/14 19:11
 */
public class Client {


    public Client(String ip, int port) {
        init(ip, port);
    }

    private void init(String ip, int port) {

        EventLoopGroup connection = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();

        client.group(connection)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_SNDBUF, 2 * 1024)
                .option(ChannelOption.SO_RCVBUF, 2 * 1024)
                .handler(new ClientInitializer());

        try {

            ChannelFuture future = client.connect(ip, port).sync();

            System.out.println("client connect.....");
            send(future);

            future.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {

            connection.shutdownGracefully();

        }

    }

    /**
     * 发送消息
     * @param future
     */
    private void send(ChannelFuture future) {

        Scanner input = new Scanner(System.in);

        while (true) {
            String data = input.nextLine();

            ChatProtocol protocol = new ChatProtocol(data.getBytes().length, data.getBytes());
            future.channel().writeAndFlush(protocol);
        }

    }

}
    