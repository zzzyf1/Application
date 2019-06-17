package com.example.zyf.application.Utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zyf.application.Administration.eduApplication;
import com.example.zyf.application.DetailActivity;
import com.example.zyf.application.LoginActivity;
import com.example.zyf.application.R;
import com.example.zyf.application.SuperiorActivity;

import java.util.List;



public class eduApplicationAdapter extends RecyclerView.Adapter<eduApplicationAdapter.ViewHolder> {

    private List<eduApplication> mApplication;
    //内部类实现缓存器,缓存布局以及该布局里包含的控件
    static class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        ImageView imageView;
        TextView education_id;
        TextView introduction;
        public ViewHolder(View view){
            super(view);
            itemView=view;
            imageView=view.findViewById(R.id.edu_image);
            education_id=view.findViewById(R.id.edu_id);
            introduction=view.findViewById(R.id.introduction);
        }
    }
    public eduApplicationAdapter(List<eduApplication> list){
        mApplication=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_content,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("itemPosition",holder.getAdapterPosition());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        eduApplication apply=mApplication.get(i);
        viewHolder.imageView.setImageResource(apply.getImageId());
        viewHolder.education_id.setText(apply.getApplicationId());
        viewHolder.introduction.setText(apply.getIntroduction());
    }

    @Override
    public int getItemCount() {
        return mApplication.size();
    }
}
