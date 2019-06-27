package com.example.zyf.application.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zyf.application.BoardActivity;
import com.example.zyf.application.HomeworkActivity;
import com.example.zyf.application.R;
import com.example.zyf.application.UploadCourseActivity;
import com.example.zyf.application.VideoActivity;

public class issue_fragment extends Fragment {
    private TextView board;//公告
    private TextView issue; //上传课程图片
    private TextView homework;//上传作业
    private TextView third;
    private ImageView board_image;//公告
    private ImageView issue_image;//上传课程图片
    private ImageView homework_image;//上传课程图片
    public static issue_fragment newInstance(Bundle args){
        issue_fragment fragment=new issue_fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.issue_fragment,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        issue=getActivity().findViewById(R.id.issue_image);
        board=getActivity().findViewById(R.id.issue_announce);
        homework=getActivity().findViewById(R.id.issue_homework);
        third=getActivity().findViewById(R.id.message);
        third.setVisibility(View.INVISIBLE);
        issue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent=new Intent(getActivity(),UploadCourseActivity.class);
                startActivity(intent);
                return false;
            }
        });

        board.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent=new Intent(getActivity(),BoardActivity.class);
                startActivity(intent);
                return false;
            }
        });

        homework.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent=new Intent(getActivity(),VideoActivity.class);
                startActivity(intent);
                return false;
            }
        });

    }

}
