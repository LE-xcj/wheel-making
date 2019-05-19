package com.xc.pojo.vo;

import java.util.Arrays;

/**
 * @author chujian
 * @ClassName SystemInfoVo
 * @Description 功能描述
 * @date 2019/5/18 23:47
 */
public class SystemInfoVO {

    private String ip;

    private String name;

    private String os;

    private long totalMemory;
    private long memoryUse;
    private double memoryUsePercent;

    private CPUInfoVO[] cpus;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }

    public long getMemoryUse() {
        return memoryUse;
    }

    public void setMemoryUse(long memoryUse) {
        this.memoryUse = memoryUse;
    }

    public CPUInfoVO[] getCpus() {
        return cpus;
    }

    public void setCpus(CPUInfoVO[] cpus) {
        this.cpus = cpus;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public double getMemoryUsePercent() {
        return memoryUsePercent;
    }

    public void setMemoryUsePercent(double memoryUsePercent) {
        this.memoryUsePercent = memoryUsePercent;
    }

    @Override
    public String toString() {
        return "SystemInfoVO{" +
                "ip='" + ip + '\'' +
                ", name='" + name + '\'' +
                ", os='" + os + '\'' +
                ", totalMemory=" + totalMemory +
                ", memoryUse=" + memoryUse +
                ", memoryUsePercent=" + memoryUsePercent +
                ", cpus=" + Arrays.toString(cpus) +
                '}';
    }
}

    