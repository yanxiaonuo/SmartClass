package com.xiaonuo.smartclass.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Utils {

    private static SharedPreferences sp;


    /**
     * @param value the value will be show by Toast
     */
    public static void show(String value) {
        Toast.makeText(MyApplication.getContext(), value, Toast.LENGTH_SHORT).show();
    }

    public static boolean cleanSp() {
        if (sp == null) {
            sp = MyApplication.getContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        return sp.edit().clear().commit();
    }


    /**
     * @param key   键
     * @param value 值
     * @return true or false
     */
    public static boolean putString(String key, String value) {
        if (sp == null) {
            sp = MyApplication.getContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        return sp.edit().putString(key, value).commit();
    }


    /**
     * @param key          键
     * @param defaultValue 默认值
     * @return 取出值
     */
    public static String getString(String key, String defaultValue) {
        if (sp == null) {
            sp = MyApplication.getContext()
                    .getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        return sp.getString(key, defaultValue);
    }

}
