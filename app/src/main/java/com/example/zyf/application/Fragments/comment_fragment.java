package com.example.zyf.application.Fragments;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyf.application.Db.comment;
import com.example.zyf.application.R;
import com.example.zyf.application.Utils.AsyncHttpUtil;
import com.example.zyf.application.Utils.CommentAdapter;
import com.example.zyf.application.Utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class comment_fragment extends Fragment {

    private List<comment> comments=new ArrayList<>();
    private List<comment> Tcomments=new ArrayList<>();
    private TextView second;
    private CommentAdapter adapter;
    private String course_id="123@qwq";
    private TextView search;

    public static comment_fragment newInstance(Bundle args){
        comment_fragment fragment=new comment_fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.comment_fragment,container,false);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        second=getActivity().findViewById(R.id.message);
        search=getActivity().findViewById(R.id.SearchText);
        RecyclerView mRecyclerView=getActivity().findViewById(R.id.commentRecycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        adapter=new CommentAdapter(comments,getActivity());
        //设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mRecyclerView.setAdapter(adapter);
        Bundle bundle=getArguments();
        //根据course_id返回指定评论
        course_id=bundle.getString("id");
        initComments(course_id,"1");

        //final ImageView test=getActivity().findViewById(R.id.TEST);
        //测试
        ImageButton imageButton=getActivity().findViewById(R.id.SearchButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchID=search.getText().toString().trim();
                //搜索指定邮箱的评论
                initComments(searchID,"0");
                /*上传测试
                if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbums();
                }
                */
                //下载测试 http://49.140.124.219:8081/myApplication/files/1.jpg
                /*String url="http://49.140.124.219:8081/myApplication/files/1.jpg";
                Glide.with(v.getContext()).load(url).placeholder(R.mipmap.cover).error(android.R.drawable.stat_notify_error).into(test);
                */
            }
        });
    }
    public void initComments(String id,String type){

        //向服务器请求有关评论
        AsyncHttpUtil.sendOkHttpRequest("http://49.140.124.219:8081/myApplication/CommentList", id,type, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Tcomments=resolveJson.getCommentList(response.body().string());
                comments.clear();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<Tcomments.size();i++){
                            comments.add(Tcomments.get(i));
                        }
                        if(Tcomments.size()==0){
                            if(second.getVisibility()==View.INVISIBLE)
                                second.setVisibility(View.VISIBLE);
                            second.setText("没有相关评论");
                        }else{
                            if(second.getVisibility()==View.VISIBLE)
                                second.setVisibility(View.INVISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }
    /*
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
                    Toast.makeText(getActivity(),"请在开启权限",Toast.LENGTH_SHORT).show();
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
        String imagePath="";
        Uri uri=intent.getData();
        if(DocumentsContract.isDocumentUri(getActivity(),uri)){
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
        Log.d("comment_fragment",imagePath);
        uploadImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过uri和selection获取图片真实路径
        Cursor cursor=getActivity().getContentResolver().query(uri,null,selection,null,null);
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"上传失败，请稍后重试",Toast.LENGTH_SHORT).show();
                    }
                });

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String returnCode=response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(returnCode.equals("1")){
                            Toast.makeText(getActivity(),"上传成功",Toast.LENGTH_SHORT).show();
                        }else if(returnCode.equals("-1")){
                            Toast.makeText(getActivity(),"不要重复上传图片",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),"上传失败，请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    */
}
