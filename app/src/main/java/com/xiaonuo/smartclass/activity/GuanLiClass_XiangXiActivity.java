package com.xiaonuo.smartclass.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.xiaonuo.smartclass.R;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;


public class GuanLiClass_XiangXiActivity extends AppCompatActivity implements View.OnClickListener{

    //服务器需要
    Socket mSocket = null;
    PrintWriter printWriter = null;
    InputStream in;

    int pos;

    Button diandeng_button;

    Button fengShan_button;

    TextView dianDeng;
    TextView fengShan;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanliclass_xiangxi);


        //设置按钮监听
        findViewById(R.id.guanLiClass_xiangXi_button_back).setOnClickListener(this);

        diandeng_button = (Button) findViewById(R.id.guanLiClass_xiangXi_button_diandeng);
        diandeng_button.setOnClickListener(this);

        fengShan_button = (Button) findViewById(R.id.guanLiClass_xiangXi_button_fengshan);
        fengShan_button.setOnClickListener(this);


        TextView className = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_className);

        dianDeng = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_dianDengQingKuang);
        fengShan = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_fengShanQingKuang);
        TextView isShangKe = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_isShangKe);
        TextView liangdu = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_liangdu);
        TextView theNumberOfPeople = (TextView) findViewById(R.id.guanLiClass_xiangXi_textView_theNumberOfPeople);

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
        if(dianDeng.getText().equals("运行")){//关闭风扇操作
            diandeng_button.setText("打开电灯");
            dianDeng.setText("关闭");
        }else {
            diandeng_button.setText("关闭电灯");
            dianDeng.setText("运行");
        }

    }



    private void startFengShanButton() {
        if(fengShan.getText().equals("运行")){//关闭风扇操作
            fengShan_button.setText("打开空调");
            fengShan.setText("关闭");
        }else {
            fengShan_button.setText("关闭空调");
            fengShan.setText("运行");
        }

    }


    public void exit(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
    }
}
