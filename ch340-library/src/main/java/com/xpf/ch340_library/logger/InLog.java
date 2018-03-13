package com.xpf.ch340_library.logger;

import android.util.Log;

/**
 * Created by xpf on 2017/9/22 :)
 * Function:Anloq Indoor logger.
 */

public class InLog {

    private static boolean mDebug = true;// default open log print.

    private InLog() {
    }

    public static void isDebug(boolean isDebug) {
        mDebug = isDebug;
    }

    public static void e(String TAG, String message) {
        if (mDebug) {
            Log.e(TAG, message);
        }
    }


    public static void i(String TAG, String message) {
        if (mDebug) {
            Log.i(TAG, message);
        }
    }

    public static void d(String TAG, String message) {
        if (mDebug) {
            Log.d(TAG, message);
        }
    }

    public static void v(String TAG, String message) {
        if (mDebug) {
            Log.v(TAG, message);
        }
    }

    public static void w(String TAG, String message) {
        if (mDebug) {
            Log.w(TAG, message);
        }
    }

    public static void wtf(String TAG, String message) {
        if (mDebug) {
            Log.wtf(TAG, message);
        }
    }
}
