package com.xpf.ch340_library;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.xpf.ch340_library.driver.InitCH340;

/**
 * Created by xpf on 2018/1/19 :)
 * Function:CH340Master 全局初始化类
 */
public class CH340Master extends Application {

    /**
     * Global application context.
     */
    @SuppressLint("StaticFieldLeak")
    static Context sContext;

    /**
     * Application context is null.
     */
    public static final String APPLICATION_CONTEXT_IS_NULL = "Application context is null. Maybe you need call CH340Master.initialize(Context) method.";

    /**
     * Construct of CommonApplication. Initialize application context.
     */
    public CH340Master() {
        sContext = this;
    }

    /**
     * Use initialize(Context).
     *
     * @param context Application context.
     */
    public static void initialize(Context context) {
        sContext = context;
        initCH340Driver();
    }

    /**
     * init init CH340 driver.
     */
    private static void initCH340Driver() {
        InitCH340.initCH340(sContext);
    }

    /**
     * Get the global application context.
     *
     * @return Application context.
     */
    public static Context getAppContext() {
        if (sContext == null) {
            throw new NullPointerException(APPLICATION_CONTEXT_IS_NULL);
        }
        return sContext;
    }
}
