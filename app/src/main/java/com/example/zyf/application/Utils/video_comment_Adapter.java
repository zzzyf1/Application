package com.example.zyf.application.Utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zyf.application.Db.comment;
import com.example.zyf.application.R;

import java.util.List;

public class video_comment_Adapter extends RecyclerView.Adapter <video_comment_Adapter.ViewHolder>{

    private List<comment> comments;
    private Activity test;

    //内部类实现缓存器,缓存布局以及该布局里包含的控件
    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView mCommentId;
        TextView mCommentDate;
        TextView mComment;
        TextView mReply;
        public ViewHolder(View view) {
            super(view);
            mCommentId=view.findViewById(R.id.commentId2);
            mCommentDate=view.findViewById(R.id.commentDate2);
            mComment=view.findViewById(R.id.comment2);
            mReply=view.findViewById(R.id.reply_video);

        }
    }
    public video_comment_Adapter(List<comment> commentList, Activity activity){
        comments=commentList;
        test=activity;
    }


    @NonNull
    @Override
    public video_comment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video_comment,viewGroup,false);
        final video_comment_Adapter.ViewHolder holder=new video_comment_Adapter.ViewHolder(view);
        holder.mReply.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //查看当前评论的回复
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        comment showComment=comments.get(i);
        viewHolder.mCommentId.setText(showComment.getCommentator());//评论人
        viewHolder.mCommentDate.setText(String.format("%tF",showComment.getDate()));//评论日期
        viewHolder.mComment.setText(showComment.getComment());//评论内容
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
