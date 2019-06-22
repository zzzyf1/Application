package com.example.zyf.application;

import android.content.Intent;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyf.application.Utils.AsyncHttpUtil;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    private String returnCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Intent intent=getIntent();
        final String eduID=intent.getStringExtra("eduID");
        final String edu_email=intent.getStringExtra("edu_email");
        final String edu_phone=intent.getStringExtra("edu_phone");
        final String edu_address=intent.getStringExtra("edu_address");
        final String edu_domain=intent.getStringExtra("edu_domain");
        final String edu_min_max=intent.getStringExtra("edu_min_max");
        final String edu_introduction=intent.getStringExtra("edu_introduction");
        final String edu_password=intent.getStringExtra("edu_password");

        TextView idTextView=findViewById(R.id.edu_id2);
        TextView emailTextView=findViewById(R.id.edu_email2);
        TextView phoneTextView=findViewById(R.id.edu_phone2);
        TextView addressTextView=findViewById(R.id.edu_address2);
        TextView domain_TextView=findViewById(R.id.edu_domain2);
        TextView ageTextView=findViewById(R.id.edu_age2);
        TextView introductionTextView=findViewById(R.id.edu_introdution2);
        idTextView.setText(eduID);
        emailTextView.setText(edu_email);
        phoneTextView.setText(edu_phone);
        addressTextView.setText(edu_address);
        domain_TextView.setText(edu_domain);
        ageTextView.setText(edu_min_max);
        introductionTextView.setText(edu_introduction);
        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AsyncHttpUtil.sendOkHttpRequest("http://49.140.124.219:8081/myApplication/approveEmail", edu_email, edu_password, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        DetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DetailActivity.this,"网络繁忙",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        // “-1”,更新失败 “0”更新成功
                        returnCode=response.body().string();
                        DetailActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /*
                                * 有关控件着色的问题提，着色其Tint，没整明白
                                * 其他两种方法都不行*/
                                if(returnCode.equals("0")){
                                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF7AD66C")));//#F0625E
                                    //fab.setBackgroundColor(Color.rgb(255,0,0));
                                    //fab.setBackgroundResource(R.color.colorAccent);
                                    Toast.makeText(DetailActivity.this,"审核通过",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(DetailActivity.this,"网络繁忙，信息更新失败",Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    }
                });

            }
        });
    }
}
