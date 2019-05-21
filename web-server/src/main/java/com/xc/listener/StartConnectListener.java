package com.xc.listener;

import com.xc.constant.ConstantValue;
import com.xc.server.WebServerAlive;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * @author chujian
 * @ClassName StartConnectListener
 * @Description 功能描述
 * @date 2019/5/19 23:10
 */
public class StartConnectListener implements ChannelFutureListener {

    public void operationComplete(ChannelFuture future) throws Exception {

        if (future.isSuccess()) {

            System.out.println("连接成功........");

        } else {

            Thread.sleep(1000);

            WebServerAlive.connect(ConstantValue.HEARTBEAT_DETECT_IP, ConstantValue.HEARTBEAT_DETECT_PORT);

        }

    }

}
    