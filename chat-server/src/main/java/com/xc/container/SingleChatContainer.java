package com.xc.container;

import com.xc.pojo.entity.ChannelHandlerContextEntity;

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

    private static Map<String, ChannelHandlerContextEntity> single;

    static {
        single = new ConcurrentHashMap<>();
    }


    /**
     * 以用户的id为key，channel为value
     * @param source
     * @param context
     */
    public static void add (String source, ChannelHandlerContextEntity context) {
        single.put(source, context);
    }

    public static void remove (String source) {
        single.remove(source);
    }

    public static ChannelHandlerContextEntity getChannel (String id) {
        return single.get(id);
    }
}
    