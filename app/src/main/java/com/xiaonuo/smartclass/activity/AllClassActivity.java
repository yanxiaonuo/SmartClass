package com.xiaonuo.smartclass.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

public class AllClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allclass);
        StatusBarUtil.setTransparent(this);

        YLListView listView = findViewById(R.id.allClass_listView);
        // 不添加也有默认的头和底
        View topView = View.inflate(this, R.layout.item_allcalss_list_head, null);
        listView.addHeaderView(topView);
//        View bottomView=new View(getApplicationContext());
//        listView.addFooterView(bottomView);

        // 顶部和底部也可以固定最终的高度 不固定就使用布局本身的高度
//        listView.setFinalBottomHeight(100);
        listView.setFinalTopHeight(200);

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {

                // TODO: 2018/2/1 演示逻辑，由打开的按钮来和人数来决定是否显示
                String openBy = Utils.getString(Constant.OPENBY, "");
                if (openBy == Constant.OPENBYALLCLASS) {
                    //所有教室
                    return 1;
                } else {
                    Integer count = Integer.valueOf(Utils.getString(Constant.PEOPLECOUNT, "0"));
                    if(count >Constant.CLASSCRITICALITY){
                        //该教室非空闲
                        return 0;
                    }else{
                        return 1;
                    }
                }
            }

            @Override
            public Object getItem(int position) {
                return position + 1 + "";
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = View.inflate(MyApplication.getContext(), R.layout.item_allclass_list, null);
                TextView tv_classStatus = view.findViewById(R.id.item_allClassSimpleMessage_textView_classStatus);
                TextView tv_peopleCount = view.findViewById(R.id.item_allClassSimpleMessage_textView_peopleCount);

                tv_peopleCount.setText(Utils.getString(Constant.PEOPLECOUNT, "") + "/" + Constant.CLASSMAXCOUNT);
                tv_classStatus.setText(Utils.getString(Constant.CLASSSTATUS, ""));
                return view;
            }
        });

        //YLListView默认有头和底  处理点击事件位置注意减去
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){
                String temperature = Utils.getString(Constant.TEMPERATURE, "");
                String peopleCount = Utils.getString(Constant.PEOPLECOUNT, "");
                String brightness = Utils.getString(Constant.BRIGHTNESS, "");
                String classStatus = Utils.getString(Constant.CLASSSTATUS, "");
                String fan = Utils.getString(Constant.FAN, "");
                String light = Utils.getString(Constant.LIGHT, "");

                //数据展示
                String data = "温度：        " + temperature + "℃\r\n" +
                        "人数：        " + peopleCount + "\r\n" +
                        "亮度：        " + brightness + "\r\n" +
                        "上课情况：" + classStatus + "\r\n" +
                        "风扇：        " + fan + "\r\n" +
                        "电灯情况：" + light;

                //只有一个ITEM或0个
                new MaterialDialog.Builder(AllClassActivity.this)
                        .title("1号教室")
                        .content(data)
                        .positiveText("OK")
                        .show();
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
        overridePendingTransition(R.anim.pre_in, R.anim.pre_out);
    }
}
