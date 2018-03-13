package com.xpf.ch340_library.runnable;

import com.xpf.ch340_library.driver.InitCH340;
import com.xpf.ch340_library.logger.InLog;
import com.xpf.ch340_library.utils.CH340Util;

/**
 * Created by xpf on 2017/12/20.
 * Function:ReadDataRunnable
 */

public class ReadDataRunnable implements Runnable {

    private String TAG = ReadDataRunnable.class.getSimpleName();
    private boolean mStop = false; // 是否停止线程

    @Override
    public void run() {
        startReadThread();
    }

    /**
     * 开启读取数据线程
     */
    private void startReadThread() {
        while (!mStop) {
            byte[] receiveBuffer = new byte[32];// 接收数据数组
            // 读取缓存区的数据长度
            int length = InitCH340.getDriver().ReadData(receiveBuffer, 32);

            switch (length) {
                case 0: // 无数据
                    InLog.i(TAG, "No data~");
                    break;
                default: // 有数据时的处理
                    // 将此处收到的数组转化为HexString
                    String hexString = CH340Util.bytesToHexString(receiveBuffer, length);
                    InLog.i(TAG, "ReadHexString===" + hexString + ",length===" + length);
                    break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止读取任务
     */
    public void stopTask() {
        mStop = true;
    }

}
