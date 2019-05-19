package com.xc.utils;

import com.xc.decrytion.AbstractDecryption;
import com.xc.encryption.AbstarctEncryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author chujian
 * @ClassName GzipUtil
 * @Description 功能描述
 *
 * 过程：
 * 加密-----》 压缩
 * 解压-----》 解密
 * 要对称
 * @date 2019/5/19 9:47
 */
public class GzipUtil {

    /**
     * 不适用加密方式来压缩
     * @param source
     * @return
     * @throws IOException
     */
    public static byte[] zip(byte[] source) throws IOException {
        return zip(source, null);
    }

    /**
     * 压缩数据
     * @param source
     * @param encryption 加密方式
     * @return  压缩后数据
     * @throws IOException
     */
    public static byte[] zip(byte[] source, AbstarctEncryption encryption) throws IOException {

        // 加密
        if (encryption != null) {
            encryption.encrypte(source);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // jdk提供的压缩流
        GZIPOutputStream zipOut = new GZIPOutputStream(out);

        // 将压缩信息写入内存，写入过程实现解压
        zipOut.write(source);

        // 结束写入
        zipOut.finish();

        // 将压缩的流数据转为字节数组
        byte[] target = out.toByteArray();

        zipOut.close();
        return target;
    }

    /**
     * 不适用解密来解压
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] unzip(byte[] data) throws IOException {
        return unzip(data, null);
    }

    /**
     *
     *
     * 解压数据
     * @param data
     * @param decryption : 是否需要解密
     * @return 解压后的字节数组
     * @throws IOException
     */
    public static byte[] unzip(byte[] data, AbstractDecryption decryption) throws IOException {

        // 解压
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(data);

        // jdk提供的一种用于解压使用的流对象
        GZIPInputStream zipIn = new GZIPInputStream(in);

        byte[] buffer = new byte[1024];
        int length = 0;

        // 通过zip进行读取，解压
        while ((length = zipIn.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, length);
        }

        // 将字节流转为字节数组
        byte[] target = out.toByteArray();


        // 解密
        if (decryption != null) {
            decryption.decrypte(target);
        }

        // 挂壁资源
        zipIn.close();
        out.close();;

        return target;
    }


}
    