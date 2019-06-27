package com.example.zyf.application.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyf.application.Db.comment;
import com.example.zyf.application.R;
import com.example.zyf.application.Utils.AsyncHttpUtil;
import com.example.zyf.application.Utils.CommentAdapter;
import com.example.zyf.application.Utils.resolveJson;
import com.example.zyf.application.Utils.video_comment_Adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class video_course_fragment extends Fragment {
    private List<comment> comments=new ArrayList<>();
    private List<comment> Tcomments=new ArrayList<>();

    private video_comment_Adapter adapter;
    private String course_id="123@qwq";//默认课程ID
    private ImageView mComment;
    private EditText editText;
    private comment testComment=new comment();

    //传递参数用
    public static video_course_fragment newInstance(Bundle args){
        video_course_fragment fragment=new video_course_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.video_comment_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mComment=getActivity().findViewById(R.id.video_imageView8);
        editText=getActivity().findViewById(R.id.video_editText);
        RecyclerView mRecyclerView=getActivity().findViewById(R.id.video_comment);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        adapter=new video_comment_Adapter(comments,getActivity());
        //设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mRecyclerView.setAdapter(adapter);
        Bundle bundle=getArguments();
        //根据course_id返回指定评论
        course_id=bundle.getString("id");
        initComments(course_id,"1");

        mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Mcomment=editText.getText().toString().trim();
                //发表用户id
                if(!Mcomment.equals("")){
                    //上传评论，更新当前列表
                    AsyncHttpUtil.sendOkHttpRequest("http://49.140.124.219:8081/myApplication/addComment", "家长#"+course_id, Mcomment, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"发表评论失败，请稍后再试",Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String returnCode=response.body().string();
                            if(returnCode.equals("0")){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(),"成功发表评论",Toast.LENGTH_SHORT).show();
                                        initComments(course_id,"1");
                                    }
                                });
                            }else{
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(),"发表评论失败，请稍后再试",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"评论为空",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
    public void initComments(String id,String type) {

        //向服务器请求有关评论
        AsyncHttpUtil.sendOkHttpRequest("http://49.140.124.219:8081/myApplication/CommentList", id, type, new Callback() {
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
                Tcomments = resolveJson.getCommentList(response.body().string());
                comments.clear();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < Tcomments.size(); i++) {
                            comments.add(Tcomments.get(i));
                        }
                        /*if(Tcomments.size()==0){
                            if(second.getVisibility()==View.INVISIBLE)
                                second.setVisibility(View.VISIBLE);
                            second.setText("没有相关评论");
                        }else{
                            if(second.getVisibility()==View.VISIBLE)
                                second.setVisibility(View.INVISIBLE);
                        }*/
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }
}
