package com.xiaonuo.smartclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.a520wcf.yllistview.YLListView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jaeger.library.StatusBarUtil;
import com.xiaonuo.smartclass.R;
import com.xiaonuo.smartclass.utils.Constant;
import com.xiaonuo.smartclass.utils.MyApplication;
import com.xiaonuo.smartclass.utils.Utils;

/**
 * Created by Administrator on 2018/2/1.
 */

public class AdministratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        StatusBarUtil.setTransparent(this);

        YLListView listView =  findViewById(R.id.listView);
        // 不添加也有默认的头和底
        View topView=View.inflate(this,R.layout.item_allcalss_list_head,null);
        listView.addHeaderView(topView);
//        View bottomView=new View(getApplicationContext());
//        listView.addFooterView(bottomView);

        // 顶部和底部也可以固定最终的高度 不固定就使用布局本身的高度
//        listView.setFinalBottomHeight(100);
        listView.setFinalTopHeight(200);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {

                return 1;
            }

            @Override
            public Object getItem(int position) {
                return position+1+"";
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = View.inflate(MyApplication.getContext(), R.layout.item_administrator_list, null);
                return view;
            }
        });

        //YLListView默认有头和底  处理点击事件位置注意减去
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position==1){
                    Intent intent=new Intent(AdministratorActivity.this,GuanLiClass_XiangXiActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.next_in,R.anim.next_out);
                }
            }
        });
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
