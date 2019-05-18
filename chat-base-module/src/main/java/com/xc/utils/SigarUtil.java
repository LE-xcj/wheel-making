package com.xc.utils;


import com.xc.pojo.vo.ProcessInfoVO;
import com.xc.pojo.vo.SystemInfoVO;
import org.hyperic.sigar.Sigar;

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

        // todo


        return systemInfo;
    }


    /**
     * 获取指定进程的资源使状态信息
     * @param pid
     * @return
     */
    public static ProcessInfoVO getProcessInfo(long pid) {

        ProcessInfoVO processInfo = new ProcessInfoVO();

        // todo

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

}
    