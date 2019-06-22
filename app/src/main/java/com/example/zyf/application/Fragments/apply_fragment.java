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
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyf.application.R;
import com.example.zyf.application.Utils.*;
import com.example.zyf.application.Utils.eduApplicationAdapter;
import com.example.zyf.application.Db.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class apply_fragment extends Fragment {

    private List<edu_Information> list=new ArrayList<>();
    private List<edu_Information> Tlist=new ArrayList<>();
    private String applyListJson;
    private eduApplicationAdapter adapter;
    private TextView home;//数据加载过程中的提示信息
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.apply_fragment,container,false);

        return view;
    }
    //在onActivityCreated中调用getActivity()，否则可能会出现空异常
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        home=getActivity().findViewById(R.id.message);
        RecyclerView mRecyclerView=getActivity().findViewById(R.id.apply_recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        adapter=new eduApplicationAdapter(list);
        //设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mRecyclerView.setAdapter(adapter);
        initList();

    }
    public void initList(){
        //向服务器请求初始化教育机构申请列表
        AsyncHttpUtil.sendOkHttpRequest("http://49.140.124.219:8081/myApplication/ApplyList", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(),"网络请求错误",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                applyListJson=response.body().string();
                Tlist=resolveJson.getList(applyListJson);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        for(int i=0;i<Tlist.size();i++){
                            list.add(Tlist.get(i));
                        }
                        if(Tlist.size()==0){
                            if(home.getVisibility()==View.INVISIBLE)
                                home.setVisibility(View.VISIBLE);
                            home.setText("没有申请记录");
                        }else{
                            if(home.getVisibility()==View.VISIBLE)
                                home.setVisibility(View.INVISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
