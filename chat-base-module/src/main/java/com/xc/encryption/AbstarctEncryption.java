package com.xc.encryption;

/**
 * @author chujian
 * @ClassName AbstarctEncryption
 * @Description 功能描述
 * 抽象加密类
 * @date 2019/5/19 10:10
 */
public abstract class AbstarctEncryption {

    /**
     * 加密
     * @param source  需要加密的字节数据
     */
    public abstract void encrypte(byte[] source);

}
    