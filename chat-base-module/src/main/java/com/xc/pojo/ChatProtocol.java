package com.xc.pojo;

import com.xc.constant.ConstantValue;

import java.net.SocketAddress;

/**
 * @author chujian
 * @ClassName ChatProtocol
 * @Description 功能描述
 * @date 2019/5/14 18:19
 */
public class ChatProtocol {

    /**
     * 头部信息
     * 1、标志
     * 2、内容长度
     */
    private int head = ConstantValue.HEAD_DATA;
    private int contentLength;

    //请求体中内容
    private byte[] content;

    //来源
    private SocketAddress source;

    public ChatProtocol(int contentLength, byte[] content) {
        this.contentLength = contentLength;
        this.content = content;
    }

    public ChatProtocol(int contentLength, byte[] content, SocketAddress source) {
        this.contentLength = contentLength;
        this.content = content;
        this.source = source;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public SocketAddress getSource() {
        return source;
    }

    public void setSource(SocketAddress source) {
        this.source = source;
    }
}
    