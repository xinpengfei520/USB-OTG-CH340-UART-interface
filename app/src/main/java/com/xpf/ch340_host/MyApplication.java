package com.xpf.ch340_host;

import android.app.Application;
import android.content.Context;

/**
 * Created by xpf on 2018/3/12 :)
 * Function:
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * 获取全局上下文
     *
     * @return Application context
     */
    public static Context getContext() {
        return mContext;
    }
}
