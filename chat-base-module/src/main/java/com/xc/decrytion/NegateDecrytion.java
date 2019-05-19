package com.xc.decrytion;

/**
 * @author chujian
 * @ClassName NegateDecrytion
 * @Description 功能描述
 * @date 2019/5/19 10:32
 */
public class NegateDecrytion extends AbstractDecryption {

    public void decrypte(byte[] source) {

        for (int i=0; i<source.length; ++i) {
            source[i] = (byte) (0 - source[i]);
        }
    }

}
    