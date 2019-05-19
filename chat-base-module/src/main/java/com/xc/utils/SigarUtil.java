package com.xc.utils;


import com.xc.pojo.vo.CPUInfoVO;
import com.xc.pojo.vo.ProcessInfoVO;
import com.xc.pojo.vo.SystemAndProcessVO;
import com.xc.pojo.vo.SystemInfoVO;
import org.hyperic.sigar.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chujian
 * @ClassName SigarUtil
 * @Description 功能描述
 * @date 2019/5/18 23:46
 */

public class SigarUtil {

    private static Sigar sigar = new Sigar();

    /**
     * 获取系统的资源状况信息
     * @return
     */
    public static SystemInfoVO getSystemInfo() {

        SystemInfoVO systemInfo = new SystemInfoVO();

        fillOS(systemInfo);

        fillCpu(systemInfo);

        fillMemory(systemInfo);

        fillNet(systemInfo);

        return systemInfo;
    }

    private static void fillNet(SystemInfoVO systemInfo) {

        systemInfo.setIp(getIP());


    }

    public static String getIP() {
        // 获取主机的网路信息
        try {

            InetAddress address = address = InetAddress.getLocalHost();
            return address.getHostAddress();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void fillOS(SystemInfoVO systemInfo) {

        OperatingSystem os = OperatingSystem.getInstance();

        // 设置操作系统类型
        systemInfo.setOs(os.getArch());

        // 名称
        systemInfo.setName(os.getVendorName());


    }

    private static void fillCpu(SystemInfoVO systemInfo) {

        try {

            CpuPerc[] cpuPercList = cpuPercList = sigar.getCpuPercList();

            CPUInfoVO[] cpus = new CPUInfoVO[cpuPercList.length];

            int index = 0;
            // 获取cpu的使用率
            for (CpuPerc cpuPerc : cpuPercList) {

                cpus[index] = new CPUInfoVO();

                cpus[index].setCpuUsePercent(cpuPerc.format(cpuPerc.getUser()));

                ++index;
            }

            systemInfo.setCpus(cpus);

        } catch (SigarException e) {
            e.printStackTrace();
        }



    }




    private static void fillMemory(SystemInfoVO system) {

        try {
            Mem mem = sigar.getMem();

            // 内存总量
            long total = mem.getTotal();

            // 内存实际使用量
            long actualUsed = mem.getActualUsed();

            // 内存使用百分比
            double usedPercent = mem.getUsedPercent();

            system.setMemoryUse(actualUsed);
            system.setTotalMemory(total);
            system.setMemoryUsePercent(usedPercent);

        } catch (SigarException e) {
            e.printStackTrace();
        }

    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 获取指定进程的资源使状态信息
     * @param pid
     * @return
     */
    public static ProcessInfoVO getProcessInfo(long pid) {

        ProcessInfoVO processInfo = new ProcessInfoVO();

        try {
            ProcCpu procCpu = sigar.getProcCpu(pid);
            ProcMem procMem = sigar.getProcMem(pid);
            ProcTime procTime = sigar.getProcTime(pid);

            // pid
            processInfo.setPid(pid);

            // 进程运行开始时间
            Date startTime = new Date(procTime.getStartTime());
            processInfo.setStartTime(simpleDateFormat.format(startTime));

            // 进程CPU使用百分比
            processInfo.setCpuUsePercent(CpuPerc.format(procCpu.getPercent()));

            // 内存使用
            long size = procMem.getSize();
            processInfo.setMemoryUse(size);

            // IP
            processInfo.setIp(getIP());

            // 总内存大小
            Mem mem = sigar.getMem();
            long total = mem.getTotal();
            processInfo.setMemorySize(total);

        } catch (SigarException e) {
            e.printStackTrace();
        }

        return processInfo;
    }


    /**
     * 获取当前运行进程的资源状态信息
     * @return
     */
    public static ProcessInfoVO getProcessInfo() {

        long pid = sigar.getPid();

        return getProcessInfo(pid);

    }


    /**
     * 返回系统和进程的信息
     * @return
     */
    public static SystemAndProcessVO getSystemAndProcessInfo() {

        SystemInfoVO systemInfo = getSystemInfo();
        ProcessInfoVO processInfo = getProcessInfo();

        SystemAndProcessVO vo = new SystemAndProcessVO();
        vo.setSystemInfo(systemInfo);
        vo.setProcessInfo(processInfo);

        return vo;

    }

}
    