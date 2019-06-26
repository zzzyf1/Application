package com.example.zyf.application.Utils;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SyncHttpUtil {
    //登陆请求验证
    public static String sendOkHttpRequest(String address,String username,String password)
    {
        try{
            OkHttpClient client=new OkHttpClient.Builder().readTimeout(5,TimeUnit.SECONDS).build();
            RequestBody requestBody=new FormBody.Builder().add("username",username).add("password",password).build();
            Request request=new Request.Builder().url(address).post(requestBody).build();
            Call call=client.newCall(request);
            Response response=call.execute();
            return response.body().string();//此处不要使用toString()
        }catch (Exception e){
            e.printStackTrace();
            //请求超时
            return "请求超时";
        }
    }
    //函数重载，以json或普通字符串方式请求服务器
    public static String sendOkHttpRequest(String address,String json){
        try{
            OkHttpClient client=new OkHttpClient.Builder().readTimeout(5,TimeUnit.SECONDS).build();
            RequestBody requestBody=new FormBody.Builder().add("json",json).build();
            Request request=new Request.Builder().url(address).post(requestBody).build();
            Call call=client.newCall(request);
            Response response=call.execute();
            return response.body().string();//此处不要使用toString()
        }catch (Exception e){
            e.printStackTrace();
            //请求超时
            return "请求超时";
        }
    }

}
