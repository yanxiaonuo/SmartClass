package com.xiaonuo.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.jaeger.library.StatusBarUtil;
import com.xiaonuo.smartclass.R;
import com.xiaonuo.smartclass.utils.Utils;

/**
 * Created by Administrator on 2018/2/1.
 */

public class AdministratorVerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administratorverify);
        StatusBarUtil.setTransparent(this);




    }

    public void exit(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pre_in,R.anim.pre_out);
    }

    public void verifyPassWord(View view) {
        EditText editText_passWord = findViewById(R.id.administratorVerify_editText_passWord);

        if (editText_passWord.getText().toString().equals("666666")){
            enterAdministratorPage();
        }else {
            Utils.show("·密码错误，请重试·");
        }
    }

    private void enterAdministratorPage() {

        Intent intent=new Intent(this,AdministratorActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.next_in,R.anim.next_out);
    }
}
