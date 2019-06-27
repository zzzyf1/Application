package com.example.zyf.application.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.zyf.application.Db.comment;
import com.example.zyf.application.Utils.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

public class video_course_fragment extends Fragment {
    private List<comment> comments=new ArrayList<>();
    private List<comment> Tcomments=new ArrayList<>();
    private TextView third;
    private CommentAdapter adapter;
    private String course_id="123@qwq";//默认课程ID
    private TextView search;

    public static video_course_fragment newInstance(Bundle args){
        video_course_fragment fragment=new video_course_fragment();
        fragment.setArguments(args);
        return fragment;
    }
}
