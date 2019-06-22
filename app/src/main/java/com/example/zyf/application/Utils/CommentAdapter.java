package com.example.zyf.application.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zyf.application.Db.comment;
import com.example.zyf.application.R;


import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<comment> comments;

    //内部类实现缓存器,缓存布局以及该布局里包含的控件
    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView mCommentId;
        TextView mCommentDate;
        TextView mComment;
        public ViewHolder(View view) {
            super(view);
            mCommentId=view.findViewById(R.id.commentId);
            mCommentDate=view.findViewById(R.id.commentDate);
            mComment=view.findViewById(R.id.comment);

        }
    }
    public CommentAdapter(List<comment> commentList){
        comments=commentList;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment_content,viewGroup,false);
        final CommentAdapter.ViewHolder holder=new CommentAdapter.ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(v.getContext(), DetailActivity.class);

                v.getContext().startActivity(intent);*/
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
