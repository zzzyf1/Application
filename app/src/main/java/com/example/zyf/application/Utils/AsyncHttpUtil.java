package com.example.zyf.application.Utils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AsyncHttpUtil {
    //无参，请求数据
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder().build();
        //装载请求
        Request request=new Request.Builder().url(address).post(requestBody).build();
        //发送请求
        client.newCall(request).enqueue(callback);
    }

    //一个参数
    public static void sendOkHttpRequest(String address,String ID,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();

        RequestBody requestBody=new FormBody.Builder().add("id",ID).build();
        //装载请求
        Request request=new Request.Builder().url(address).post(requestBody).build();
        //发送请求
        client.newCall(request).enqueue(callback);
    }

    //2个参数，审核通过教育机构
    public static void sendOkHttpRequest(String address,String email,String password,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();

        RequestBody requestBody=new FormBody.Builder().add("username",email).add("password",password).build();
        //装载请求
        Request request=new Request.Builder().url(address).post(requestBody).build();
        //发送请求
        client.newCall(request).enqueue(callback);
    }


}
