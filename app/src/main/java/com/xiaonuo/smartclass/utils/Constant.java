package com.xiaonuo.smartclass.utils;

/**
 * Created by Administrator on 2018/1/29.
 */

public class Constant {

    //服务器地址该项目地址
    public static String SERVICE ="http://192.168.1.133:8080/SmartClass";
    public static String OPENBY="openByWho";
    public static String OPENBYALLCLASS="openByAllClass";
    public static String OPENBYFREECLASS="openByFreeClass";

    //服务器存储ClassStatus文件的路径
    public static String CLASSSTATUSJSONONSERVICEPATH="AppData/ClassStatus.json";

    //服务器取回存储在本机的ClassStatus文件的文件名
    public static String CLASSSTATUSJSONONPHONEPATH="lastClassStatus.json";

    //教室最大人数
    public static int CLASSMAXCOUNT=40;

    //空闲教室临界数
    public static int CLASSCRITICALITY=30;

    //json文件解析数据
    public static String TEMPERATURE="temperature";
    public static String PEOPLECOUNT="peopleCount";
    public static String BRIGHTNESS="brightness";
    public static String CLASSSTATUS="classStatus";
    public static String FAN="fan";
    public static String LIGHT="light";

}
