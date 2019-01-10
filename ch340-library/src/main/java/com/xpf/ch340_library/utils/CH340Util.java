package com.xpf.ch340_library.utils;

import android.support.annotation.NonNull;

import com.xpf.ch340_library.driver.InitCH340;
import com.xpf.ch340_library.logger.LogUtils;

/**
 * Created by xpf on 2018/2/6 :)
 * Function:CH340 数据处理工具类
 */
public class CH340Util {

    private static final String TAG = "CH340Util";

    enum DataFormat {
        ASCII, HEX
    }

    /**
     * write data in ch340.
     *
     * @param byteArray 字节数组
     * @param format    写入数据的格式
     * @return 返回写入的结果，-1 表示写入失败！
     */
    public static int writeData(@NonNull byte[] byteArray, String format) {
        // 将此处收到的数组转化为 HexString
        String hexString = bytesToHexString(byteArray, byteArray.length);
        LogUtils.i(TAG, "WriteHexString===" + hexString);
        if ("ascii".equals(format)) {
            return InitCH340.getDriver().WriteData(byteArray, byteArray.length);
        } else if ("hex".equals(format)) {
            return InitCH340.getDriver().WriteData(hexString.getBytes(), byteArray.length);
        } else {
            return -1;
        }
    }

    /**
     * byte[]转换为hexString
     *
     * @param buffer 数据
     * @param size   字符数
     * @return 返回转换后的十六进制字符串
     */
    public static String bytesToHexString(byte[] buffer, final int size) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (buffer == null || size <= 0) return null;
        for (int i = 0; i < size; i++) {
            String hex = Integer.toHexString(buffer[i] & 0xff);
            if (hex.length() < 2) stringBuilder.append(0);
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }
}
