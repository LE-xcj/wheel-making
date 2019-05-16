package com.xc.container;

import com.xc.mapper.MessageMapper;

/**
 * @author chujian
 * @ClassName MapperContainer
 * @Description 功能描述
 * @date 2019/5/16 21:33
 */
public class MapperContainer {

    private static MessageMapper messageMapper;

    public static MessageMapper getMessageMapper() {
        return messageMapper;
    }

    public static void setMessageMapper(MessageMapper messageMapper) {
        MapperContainer.messageMapper = messageMapper;
    }
}
    