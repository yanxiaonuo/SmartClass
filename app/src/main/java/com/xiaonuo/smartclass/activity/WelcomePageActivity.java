package com.xiaonuo.smartclass.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.wang.avi.AVLoadingIndicatorView;
import com.xiaonuo.smartclass.R;
import com.xiaonuo.smartclass.utils.Constant;
import com.xiaonuo.smartclass.utils.StreamUtil;
import com.xiaonuo.smartclass.utils.Utils;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WelcomePageActivity extends AppCompatActivity {

    private TextView mVersionName; //版本名称
    private RelativeLayout mBackground;//背景，用作动画
    private int mLocalVersionCode;  //版本号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        StatusBarUtil.setTransparent(this);
        //初始化UI
        initUI();

        //淡入效果动画
        setAnimation();


        //初始化数据
        initData();
    }

    private void initData() {
        String s= getLocalVersion();

        //设置版本名称
        mVersionName.setText("版本号:"+s);

        //检验是否更新
        checkVersion();
    }

    private void checkVersion() {

        //取出版本号
        mLocalVersionCode = getVersionCode();

        getLastVersion();
    }


    /**
     * 连接服务器，取出最新的版本信息
     */
    private void getLastVersion() {

        final long t1 = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    URL url=new URL(Constant.SERVICE+"/versionMessage.json");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setConnectTimeout(2000);

                    connection.setReadTimeout(2000);

                    if(connection.getResponseCode()==200){

                        InputStream inputStream = connection.getInputStream();

                        String json= StreamUtil.streamToString(inputStream);

                        JSONObject jsonObject=new JSONObject(json);

                        String lastVersionCode = jsonObject.getString("versionCode");

                        if(Integer.parseInt(lastVersionCode)>mLocalVersionCode){
                            //更新版本
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utils.show("有新的版本更新");
                                }
                            });
                        }

                    }

                } catch (Exception e) {
                    System.out.println("URL错误");
                    e.printStackTrace();
                }finally {
                    long t2 = System.currentTimeMillis();
                    if((t2-t1)<3000){
                        try {
                            Thread.sleep(3000-(t2-t1));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                enterMainActivity();
            }
        }).start();

    }

    private void enterMainActivity() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        //关闭londing,否则直接finish会不流畅
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AVLoadingIndicatorView loding=findViewById(R.id.welcomepage_lodingView_loding);
                loding.hide();
            }
        });

        //关闭欢迎页面
        finish();

    }


    /**
     * 得到版本号
     * @return 版本号，如果错误则返回0
     */
    private int getVersionCode() {
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 得到本地版本名称
     * @return 版本名称，如果错误则返回null
     */
    private String getLocalVersion() {
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 全局动画
     */
    private void setAnimation() {
        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2000);
        mBackground.startAnimation(alphaAnimation);
    }

    private void initUI() {
        //版本号
        mVersionName = findViewById(R.id.welcomePage_textView_version);
        mBackground = findViewById(R.id.welcomepage_relativeLayout_background);
    }
}
