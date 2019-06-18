package com.example.zyf.application.Utils;

import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zyf.application.Db.*;
import com.example.zyf.application.DetailActivity;

import com.example.zyf.application.R;


import java.util.List;



public class eduApplicationAdapter extends RecyclerView.Adapter<eduApplicationAdapter.ViewHolder> {

    private List<edu_Information> mApplication;
    private String applyListJson;
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
            education_id=view.findViewById(R.id.edu_email);
            introduction=view.findViewById(R.id.introduction);
        }
    }
    public eduApplicationAdapter(List<edu_Information> list){
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
                edu_Information edu=mApplication.get(holder.getAdapterPosition());
                intent.putExtra("eduID",edu.getmEducation().getEducation_id());
                intent.putExtra("edu_email",edu.getmEmail().getuEmail());
                intent.putExtra("edu_phone",edu.getmPhone().get(0).getPhoneNumber());
                intent.putExtra("edu_address",edu.getmEducation().getAddress());
                intent.putExtra("edu_domain",edu.getmEducation().getDomain());
                intent.putExtra("edu_min_max",String.valueOf(edu.getmEducation().getMinAge())+"岁-"+String.valueOf(edu.getmEducation().getMaxAge())+"岁");
                intent.putExtra("edu_introduction",edu.getmEducation().getDescribe());
                intent.putExtra("edu_password",edu.getmEmail().getuPassword());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        edu_Information apply=mApplication.get(i);
        //教育机构图片

        viewHolder.imageView.setImageResource(R.mipmap.cover);
        viewHolder.education_id.setText(apply.getmEducation().getEducation_id());
        viewHolder.introduction.setText(apply.getmEducation().getDomain());
    }

    @Override
    public int getItemCount() {
        return mApplication.size();
    }
}
