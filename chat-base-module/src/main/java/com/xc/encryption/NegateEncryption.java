package com.xc.encryption;

/**
 * @author chujian
 * @ClassName NegateEncryption
 * @Description 功能描述
 * 取反加密
 * @date 2019/5/19 10:33
 */
public class NegateEncryption extends AbstarctEncryption {

    public void encrypte(byte[] source) {

        for (int i=0; i<source.length; ++i) {
            source[i] = (byte) (0 - source[i]);
        }

    }

}
    