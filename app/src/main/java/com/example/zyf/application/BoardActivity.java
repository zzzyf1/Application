package com.example.zyf.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zyf.application.Utils.AsyncHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BoardActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private String Announcement;
    private String returnCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        //TextView test=findViewById(R.id.text);
        //test.setText("1234567890ahsduasdkbasdjbasdbakjbdasjkbdjasbdajdbakjsdbajs");
        //test.setSelected(true);
        editText=findViewById(R.id.editText_Board);
        button=findViewById(R.id.BoardDone);
        //发布公告到服务器
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Announcement=editText.getText().toString().trim();
                AsyncHttpUtil.sendOkHttpRequest("http://49.140.124.219:8081/myApplication/AnnouncementServlet", Announcement, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        BoardActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BoardActivity.this,"公告发布失败,请检查网络",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        returnCode=response.body().string();
                        BoardActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(returnCode.equals("0")){
                                    Toast.makeText(BoardActivity.this,"成功公告发布",Toast.LENGTH_SHORT).show();

                                }else if(returnCode.equals("-1")){
                                    Toast.makeText(BoardActivity.this,"公告发布失败，数据库错误",Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(BoardActivity.this,"公告发布失败,不要添加表情等",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
            }
        });
    }
}
