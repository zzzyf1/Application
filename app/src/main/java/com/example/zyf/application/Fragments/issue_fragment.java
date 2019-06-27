package com.example.zyf.application.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zyf.application.BoardActivity;
import com.example.zyf.application.HomeworkActivity;
import com.example.zyf.application.R;
import com.example.zyf.application.UploadCourseActivity;

public class issue_fragment extends Fragment {
    private TextView board;
    private TextView issue;
    private TextView homework;
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
        issue=getActivity().findViewById(R.id.uploadCourse);
        board=getActivity().findViewById(R.id.noticeBoard);
        homework=getActivity().findViewById(R.id.homework);
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布公告
                Intent intent=new Intent(getActivity(),UploadCourseActivity.class);
                startActivity(intent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传课程图片
                Intent intent=new Intent(getActivity(),BoardActivity.class);
                startActivity(intent);

            }
        });

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发布公告
                Intent intent=new Intent(getActivity(),HomeworkActivity.class);
                startActivity(intent);
            }
        });
    }

}
