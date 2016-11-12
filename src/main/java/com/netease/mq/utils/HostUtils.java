package com.netease.mq.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by hzwangyujie on 2016/11/10.
 * host util
 */
public class HostUtils {

    public static final String MAC_ADDR = getMacAddr();

    //TODO 按需替换
    public static String getMacAddr() {
        // 获得ip
        NetworkInterface netInterface = null;
        try {
            netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // 获得Mac地址的byte数组
        //TODO 有隐患
        byte[] macAddr = new byte[0];
        try {
            macAddr = netInterface.getHardwareAddress();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        // 循环输出
        StringBuilder sbd = new StringBuilder();
        for (byte b : macAddr) {
            // 这里的toHexString()是自己写的格式化输出的方法，见下步。
            sbd.append(toHexString(b)+" ");
        }
        return sbd.toString();
    }

    private static String toHexString(int integer) {
        // 将得来的int类型数字转化为十六进制数
        String str = Integer.toHexString((int) (integer & 0xff));
        // 如果遇到单字符，前置0占位补满两格
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }

}
