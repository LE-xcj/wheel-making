package com.xc.pojo.vo;

/**
 * @author chujian
 * @ClassName ProcessInfoVO
 * @Description 功能描述
 * @date 2019/5/18 22:38
 */
public class ProcessInfoVO {

    private String ip;

    private long pid;

    private String startTime;

    private int port;

    // 进程使用的内存大小
    private long memoryUse;

    // 服务器的内存总大小
    private long memorySize;

    // CPU使用百分比
    private String cpuUsePercent;

    public long getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(long memorySize) {
        this.memorySize = memorySize;
    }

    public String getCpuUsePercent() {
        return cpuUsePercent;
    }

    public void setCpuUsePercent(String cpuUsePercent) {
        this.cpuUsePercent = cpuUsePercent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getMemoryUse() {
        return memoryUse;
    }

    public void setMemoryUse(long memoryUse) {
        this.memoryUse = memoryUse;
    }

    @Override
    public String toString() {
        return "ProcessInfoVO{" +
                "ip='" + ip + '\'' +
                ", pid=" + pid +
                ", startTime='" + startTime + '\'' +
                ", port=" + port +
                ", memoryUse=" + memoryUse +
                ", memorySize=" + memorySize +
                ", cpuUsePercent='" + cpuUsePercent + '\'' +
                '}';
    }
}
    