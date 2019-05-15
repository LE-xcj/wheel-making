package com.xc.container;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chujian
 * @ClassName SingleChatContainer
 * @Description 功能描述
 *              单聊
 * @date 2019/5/15 11:03
 */
public class SingleChatContainer {

    private static Map<String, ChannelHandlerContext> single;

    static {
        single = new ConcurrentHashMap<>();
    }


    /**
     * 以用户的id为key，channel为value
     * @param source
     * @param context
     */
    public static void add (String source, ChannelHandlerContext context) {
        single.put(source, context);
    }

    public static void remove (String source) {
        single.remove(source);
    }

    public static ChannelHandlerContext getChannel (String id) {
        return single.get(id);
    }
}
    