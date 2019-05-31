package com.xc.decrytion;

/**
 * @author chujian
 * @ClassName AbstractDecryption
 * @Description 功能描述
 * 抽象解密类
 * @date 2019/5/19 10:10
 */
public abstract class AbstractDecryption {

    /**
     * 解密
     * @param source  解密的字节数组
     */
    public abstract void decrypte(byte[] source);

}
    