package com.example.zyf.application.Utils;
import android.util.Log;

import java.io.File;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AsyncHttpUtil {
    //无参，请求数据(非文件类)
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().build();
        //装载请求
        Request request = new Request.Builder().url(address).post(requestBody).build();
        //发送请求
        client.newCall(request).enqueue(callback);
    }

    //一个String参数
    public static void sendOkHttpRequest(String address, String ID, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder().add("id", ID).build();
        //装载请求
        Request request = new Request.Builder().url(address).post(requestBody).build();
        //发送请求
        client.newCall(request).enqueue(callback);
    }

    //2个参数:审核通过教育机构
    public static void sendOkHttpRequest(String address, String email, String password, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder().add("username", email).add("password", password).build();
        //装载请求
        Request request = new Request.Builder().url(address).post(requestBody).build();
        //发送请求
        client.newCall(request).enqueue(callback);
    }

    //上传照片
    public static void Upload(String address, String fileName, File file,okhttp3.Callback callback) {
        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file",fileName,RequestBody.create(MediaType.parse("image/jpg"),file)).build();
                    //装载请求
            Request request = new Request.Builder().url(address).post(requestBody).build();
            //发送请求
            client.newCall(request).enqueue(callback);
        }catch (Exception e){
            Log.d("Upload","--->ERROR");
            e.printStackTrace();
        }


    }
}
