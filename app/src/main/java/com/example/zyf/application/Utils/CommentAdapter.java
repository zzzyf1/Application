package com.example.zyf.application.Utils;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyf.application.Db.comment;
import com.example.zyf.application.R;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<comment> comments;
    private Activity test;

    //内部类实现缓存器,缓存布局以及该布局里包含的控件
    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView mCommentId;
        TextView mCommentDate;
        TextView mComment;
        ImageView mImageView;
        public ViewHolder(View view) {
            super(view);
            mCommentId=view.findViewById(R.id.commentId);
            mCommentDate=view.findViewById(R.id.commentDate);
            mComment=view.findViewById(R.id.comment);
            mImageView=view.findViewById(R.id.deleteComment);

        }
    }
    public CommentAdapter(List<comment> commentList,Activity activity){
        comments=commentList;
        test=activity;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment_content,viewGroup,false);
        final CommentAdapter.ViewHolder holder=new CommentAdapter.ViewHolder(view);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View V=v;
                final int position=holder.getAdapterPosition();
                int comment_id=comments.get(position).getComment_id();
                //根据该条评论的id删除该条评论
                AsyncHttpUtil.sendOkHttpRequest("http://49.140.124.219:8081/myApplication/deleteComment", String.valueOf(comment_id), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //失败
                        Toast.makeText(V.getContext(),"删除失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String returnCode=response.body().string();
                        if(returnCode.equals("0")){
                            //删除成功
                            CommentAdapter.this.comments.remove(position);
                            test.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CommentAdapter.this.notifyItemRemoved(position);
                                }
                            });
                            //CommentAdapter.this.notifyItemMoved(position,position+1);
                        }else{
                            //利用snackbar提示
                            Toast.makeText(V.getContext(),"删除失败",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
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
