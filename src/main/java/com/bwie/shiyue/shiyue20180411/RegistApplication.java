package com.bwie.shiyue.shiyue20180411;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 暗夜 on 2018/4/11.
 */

public class RegistApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
