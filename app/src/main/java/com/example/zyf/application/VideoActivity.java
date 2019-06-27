package com.example.zyf.application;

import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.zyf.application.Fragments.comment_fragment;
import com.example.zyf.application.Fragments.video_course_fragment;

public class VideoActivity extends AppCompatActivity {
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        //加载评论碎片
        Bundle args=new Bundle();
        args.putString("id","123@qwq");
        replacefragment(video_course_fragment.newInstance(args));
        //设置课程相关信息

        /**
         * 从服务器加载课程视频
         * */
        mVideoView=findViewById(R.id.videoView);
        //设置视频控制器
        mVideoView.setMediaController(new MediaController(this));
        //播放完成回调
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
        //设置视频路径
        //mVideoView.setVideoURI("http://49.140.124.219:8081/myApplication/deleteComment");
        mVideoView.setVideoPath("http://49.140.124.219:8081/myApplication/videos/Love_Death_Robots.mp4");
        mVideoView.start();
    }
    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.VideoframeLayout,fragment);
        fragmentTransaction.commit();
    }
}
