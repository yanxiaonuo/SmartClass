package com.xiaonuo.smartclass.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.adeel.library.easyFTP;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jaeger.library.StatusBarUtil;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wang.avi.AVLoadingIndicatorView;
import com.xiaonuo.smartclass.R;
import com.xiaonuo.smartclass.ui.WaveSwipeRefreshLayout;
import com.xiaonuo.smartclass.utils.Constant;
import com.xiaonuo.smartclass.utils.StreamUtil;
import com.xiaonuo.smartclass.utils.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 2018/1/29.
 */

public class MainActivity extends AppCompatActivity {


    private AVLoadingIndicatorView mLoding;
    private String data;
    private boolean isCanTouch = true;
    private int mErrorFalg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setColor(this, Color.parseColor("#80c2f6"));

        initUi();
    }


    /**
     * 获取控件并设置监听逻辑
     */
    private void initUi() {
        //加载Loding
        mLoding = findViewById(R.id.main_lodingView_loding);
        mLoding.smoothToHide();//将其隐藏

        //打开侧栏按钮
        FancyButton fancyButton_openDrawer = findViewById(R.id.main_fancyButton_openDrawer);
        //侧边栏控件
        final FlowingDrawer flowingDrawerLayout_drawer = findViewById(R.id.main_flowingDrawerLayout_drawer);

        //全屏侧拉模式
        flowingDrawerLayout_drawer.setTouchMode(ElasticDrawer.TOUCH_MODE_FULLSCREEN);

        //监测侧拉栏状态
        flowingDrawerLayout_drawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });

        //打开侧栏按钮的监听
        fancyButton_openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开侧拉栏
                flowingDrawerLayout_drawer.openMenu();
            }
        });

        //下滑刷新
        final WaveSwipeRefreshLayout waveSwipeRefreshLayout_swipe = findViewById(R.id.main_waveSwipeRefreshLayout_swipe);
        waveSwipeRefreshLayout_swipe.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

//                        getAndStoreServiceData();
                        getAndStoreServiceData11();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(mErrorFalg==0){
                                    Utils.show("刷新成功");
                                }else{
                                    Utils.show("刷新失败");
                                }

                                waveSwipeRefreshLayout_swipe.setRefreshing(false);
                            }

                        });


                    }
                };

                timer.schedule(task, 1500);
            }
        });


        //获取查询教室控件
        FancyButton findClassButton = findViewById(R.id.main_fancyButton_findClassButton);

        //设置查询教室的监听
        findClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询教室的逻辑
                findClass();
            }
        });


        //获取所有教室控件
        FancyButton fancyButton_allClass = findViewById(R.id.main_fancyButton_allClass);

        //设置所有教室按钮的监听
        fancyButton_allClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allClass();
            }
        });


        //获取空闲教室控件
        FancyButton fancyButton_freeClass = findViewById(R.id.main_fancyButton_freeClass);

        //设置所有教室按钮的监听
        fancyButton_freeClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freeClass();
            }
        });


        //获取管理员按钮控件
        FancyButton fancyButton_administrator = findViewById(R.id.main_fancyButton_administrator);

        //设置管理员按钮的监听
        fancyButton_administrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdministratorPage();
            }
        });

    }

    private void getAndStoreServiceData11() {
        try {
            Socket socket = new Socket("192.168.31.197", 8000);
            PrintWriter write = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//            write.println("AP");
//            write.flush();
            String s=in.readLine();
            System.out.println(s);

            write.println("AP");
            write.flush();
            String json=in.readLine();


            System.out.println("111111server  " + json);



            json=json.substring(1,json.length());

            JSONObject j = new JSONObject(json);
            String classID = j.getString("id");
            String temperature = j.getString("temperature");
            String peopleCount = j.getString("people");

            String brightness = j.getString("brightness").trim();
            if (brightness.equals("1")) {
                brightness = "昏暗";
            } else if (brightness.equals("2")) {
                brightness = "中等";
            } else {
                brightness = "明亮";
            }

            String classStatus = j.getString("classstatus").trim();
            if (classStatus.equals("InClass")) {
                classStatus = "上课中";
            } else {
                classStatus = "未上课";
            }

            String fan = j.getString("fan").trim();
            if (fan.equals("ON")) {
                fan = "运行";
            } else {
                fan = "关闭";
            }

            String light = j.getString("light").trim();
            if (light.equals("ON")) {
                light = "运行";
            } else {
                light = "关闭";
            }

            //存储SP
            Utils.putString(Constant.CLASSID, classID);
            Utils.putString(Constant.TEMPERATURE, temperature);
            Utils.putString(Constant.PEOPLECOUNT, peopleCount);
            Utils.putString(Constant.BRIGHTNESS, brightness);
            Utils.putString(Constant.CLASSSTATUS, classStatus);
            Utils.putString(Constant.FAN, fan);
            Utils.putString(Constant.LIGHT, light);

            //4、关闭资源
            write.close(); // 关闭Socket输出流
            in.close(); // 关闭Socket输入流
            socket.close(); // 关闭Socket
            mErrorFalg = 0;
        } catch (Exception e) {
            mErrorFalg = 1;
            System.out.println("can not listen to:" + e);// 出错，打印出错信息
        }
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                easyFTP ftp = new easyFTP();
                ftp.connect("121.42.190.173", "iot", "cyn18773299556");
                ftp.downloadFile(Constant.CLASSSTATUSJSONONSERVICEPATH, String.valueOf(new File(MainActivity.this.getFilesDir(), Constant.CLASSSTATUSJSONONPHONEPATH)));
                return new String("Download Successful");
            } catch (Exception e) {
                String t = "Failure : " + e.getLocalizedMessage();
                return t;
            }
        }
    }

    private void openAdministratorPage() {
        Intent intent = new Intent(this, AdministratorVerifyActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }

    private void freeClass() {
        Intent intent = new Intent(this, AllClassActivity.class);
        Utils.putString(Constant.OPENBY, Constant.OPENBYFREECLASS);
        startActivity(intent);
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }

    private void allClass() {
        Intent intent = new Intent(this, AllClassActivity.class);
        Utils.putString(Constant.OPENBY, Constant.OPENBYALLCLASS);
        startActivity(intent);
        overridePendingTransition(R.anim.next_in, R.anim.next_out);
    }

    private void findClass() {
        //禁止结果未出时再点击
        if (!isCanTouch)
            return;

        isCanTouch = false;
        //查询教室EditText的获取
        MaterialEditText findClassEditText = findViewById(R.id.main_materialEditText_findClass);

        String findClassEditInPut = findClassEditText.getText().toString();

        //验证字符是否合格
        // TODO: 2018/2/1 未验证字符输入是否正确
        boolean isStringPass = verifyString(findClassEditInPut);

        if (isStringPass) {
            //开始解析数据

            //展示Loding
            mLoding.show();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        try {

                            String temperature = Utils.getString(Constant.TEMPERATURE, "");
                            String peopleCount = Utils.getString(Constant.PEOPLECOUNT, "");
                            String brightness = Utils.getString(Constant.BRIGHTNESS, "");
                            String classStatus = Utils.getString(Constant.CLASSSTATUS, "");
                            String fan = Utils.getString(Constant.FAN, "");
                            String light = Utils.getString(Constant.LIGHT, "");

                            //数据展示
                            data = "温度：        " + temperature + "℃\r\n" +
                                    "人数：        " + peopleCount + "\r\n" +
                                    "亮度：        " + brightness + "\r\n" +
                                    "上课情况：" + classStatus + "\r\n" +
                                    "风扇情况：" + fan + "\r\n" +
                                    "电灯情况：" + light;

                            // TODO: 2018/3/16    picture未解析

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //处理完毕，隐藏Loding,展示dialog
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoding.hide();

                            //展示dialog
                            new MaterialDialog.Builder(MainActivity.this)
                                    .title("1号教室")
                                    .content(data)
                                    .positiveText("OK")
//                                    .iconRes(R.mipmap.picture)
                                    .show();

                            //显示完毕，按钮再次可以被点击
                            isCanTouch = true;
                        }
                    });

                }
            }).start();

        } else {
            Utils.show("·字符格式错误，请核实后再次查询·");
        }


    }

    private boolean verifyString(String findClassEditInPut) {

        // TODO: 2018/2/1 未验证 
        return true;
    }


}
