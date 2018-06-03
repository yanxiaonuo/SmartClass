package com.xiaonuo.smartclass.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2018/1/29.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }


    /**
     * @return Context
     */
    public static Context getContext(){
        return mContext;
    }
}
