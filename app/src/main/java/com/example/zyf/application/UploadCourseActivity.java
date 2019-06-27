package com.example.zyf.application;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zyf.application.Utils.AsyncHttpUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UploadCourseActivity extends AppCompatActivity {
    private String imagePath="#";
    private ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_course);
        final ImageView imageView=findViewById(R.id.selectCourseCover);
        Button upLoadButton=findViewById(R.id.UpLoadCou);
        imageView2=findViewById(R.id.Cou_imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(UploadCourseActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(UploadCourseActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbums();
                    //Glide.with(v.getContext()).load(imagePath).error(android.R.drawable.stat_notify_error).into(imageView2);
                }

            }
        });

        upLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imagePath.equals("#")){
                    //uploadImage(imagePath);
                    Log.d("UploadCourseActivity",imagePath);
                }else{
                    Toast.makeText(UploadCourseActivity.this,"请先选择图片",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }





    private void openAlbums(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }
    //请求读取存储权限声明
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbums();
                }else{
                    Toast.makeText(UploadCourseActivity.this,"请在开启权限",Toast.LENGTH_SHORT).show();
                }
                break;//不要忘记
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 2:
                if(resultCode==RESULT_OK){
                    handleImage(data);
                }
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImage(Intent intent){
        Uri uri=intent.getData();
        if(DocumentsContract.isDocumentUri(UploadCourseActivity.this,uri)){
            //document类型的uri
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri=ContentUris.withAppendedId(Uri.parse("content://download/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            //content类型的uri普通处理即可
            imagePath=getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //file类型的uri直接读取即可
            imagePath=uri.getPath();
        }
        //Log.d("comment_fragment",imagePath);
        display(imagePath);

    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过uri和selection获取图片真实路径
        Cursor cursor=UploadCourseActivity.this.getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void  uploadImage(String filePath){
        File file=new File(filePath);
        String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
        AsyncHttpUtil.Upload("http://49.140.124.219:8081/myApplication/Upload",fileName,file,new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                UploadCourseActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UploadCourseActivity.this,"上传失败，请稍后重试",Toast.LENGTH_SHORT).show();
                    }
                });

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String returnCode=response.body().string();
                UploadCourseActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(returnCode.equals("1")){
                            Toast.makeText(UploadCourseActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                        }else if(returnCode.equals("-1")){
                            Toast.makeText(UploadCourseActivity.this,"不要重复上传图片",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UploadCourseActivity.this,"上传失败，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    private void display(String url){
        if(!imagePath.equals("#")){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            imageView2.setImageBitmap(bitmap);
        }else {
            Toast.makeText(UploadCourseActivity.this,"请先选择图片",Toast.LENGTH_SHORT).show();
        }
    }
}
