package com.xc.pojo.vo;

/**
 * @author chujian
 * @ClassName ProcessInfoVO
 * @Description 功能描述
 * @date 2019/5/18 22:38
 */
public class ProcessInfoVO {

    private String ip;

    private String pid;

    private String startTime;

    private int port;

    // 进程使用的内存大小
    private long memoryUse;

    // 消耗的cpu
    private long cpuUse;


    // 服务器的内存总大小
    private long memorySize;

    // cpu的资源总量
    private long cpuSize;


}
    