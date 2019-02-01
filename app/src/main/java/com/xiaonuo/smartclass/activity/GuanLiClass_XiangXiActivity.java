package com.xiaonuo.smartclass.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xiaonuo.smartclass.R;
import com.xiaonuo.smartclass.utils.Constant;
import com.xiaonuo.smartclass.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class GuanLiClass_XiangXiActivity extends AppCompatActivity implements View.OnClickListener{

    //ap0   风扇
    //ap1   电灯

    //温度
    int t=20;

    Button fan_button;

    Button light_button;

    TextView light_textView;
    TextView fan_textView;
    private PrintWriter write;
    private BufferedReader in;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanliclass_xiangxi);

        initTCP();


        String classID = Utils.getString(Constant.CLASSID, "");
        String peopleCount = Utils.getString(Constant.PEOPLECOUNT, "");
        String brightness = Utils.getString(Constant.BRIGHTNESS, "");
        String classStatus = Utils.getString(Constant.CLASSSTATUS, "");
        String fan = Utils.getString(Constant.FAN, "");
        String light = Utils.getString(Constant.LIGHT, "");

        //设置按钮监听
        findViewById(R.id.guanLiClass_xiangXi_button_back).setOnClickListener(this);

        fan_button = (Button) findViewById(R.id.guanLiClass_xiangXi_button_fengshan);
        fan_button.setOnClickListener(this);
        if(fan.equals("运行")){
            fan_button.setText("关闭风扇");
        }else{
            fan_button.setText("打开风扇");
        }

        light_button = (Button) findViewById(R.id.guanLiClass_xiangXi_button_diandeng);
        light_button.setOnClickListener(this);
        if(light.equals("运行")){
            light_button.setText("关闭电灯");
        }else{
            light_button.setText("打开电灯");
        }


        TextView classID_textView = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_className);
        classID_textView.setText(classID+"号教室");

        light_textView = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_dianDengQingKuang);
        fan_textView = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_fengShanQingKuang);
        light_textView.setText(light);
        fan_textView.setText(fan);

        TextView classStatus_textView = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_isShangKe);
        classStatus_textView.setText(classStatus);

        TextView brightness_textView = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_liangdu);
        brightness_textView.setText(brightness);

        TextView peopleCount_textView = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_theNumberOfPeople);
        peopleCount_textView.setText(peopleCount);
    }

    private void initTCP() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket("192.168.31.197", 8000);

                    write = new PrintWriter(socket.getOutputStream(),true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                } catch (IOException e) {
                e.printStackTrace();
            }
            }
        }).start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.guanLiClass_xiangXi_button_fengshan:
                    startFengShanButton();
                break;

            case  R.id.guanLiClass_xiangXi_button_diandeng:
                    startDianDengButton();
                break;

            case  R.id.guanLiClass_xiangXi_button_back:
                onBackPressed();
                break;
        }
    }

    private void startDianDengButton() {
        if(light_textView.getText().equals("运行")){//关闭风扇操作
            light_button.setText("打开电灯");
            light_textView.setText("关闭");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    write.println("AP10");
                    write.flush();
                }
            }).start();

        }else {
            light_button.setText("关闭电灯");
            light_textView.setText("运行");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    write.println("AP11");
                    write.flush();
                }
            }).start();
        }

    }



    private void startFengShanButton() {
        if(fan_textView.getText().equals("运行")){//关闭风扇操作
            fan_button.setText("打开风扇");
            fan_textView.setText("关闭");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    write.println("AP00");
                    write.flush();
                }
            }).start();

        }else {
            fan_button.setText("关闭风扇");
            fan_textView.setText("运行");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    write.println("AP01");
                    write.flush();
                }
            }).start();
        }

    }


    public void exit(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        write.close();
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
    }

    public void tiaojiekongtiao(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //展示dialog
                MaterialDialog m=   new MaterialDialog.Builder(GuanLiClass_XiangXiActivity.this)
                        .title("空调")
                        .content("设定温度"+t+"℃")
                        .positiveText("▼降低")
                        .negativeText("▲提升")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                t--;
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                t++;
                            }
                        })
                        .autoDismiss(false)
                        .show();

                //显示完毕，按钮再次可以被点击
            }
        });
    }
}
