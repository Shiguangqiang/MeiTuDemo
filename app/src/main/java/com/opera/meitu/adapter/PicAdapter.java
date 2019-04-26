package com.opera.meitu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.opera.meitu.R;
import com.opera.meitu.bean.InfoBean;

import java.util.ArrayList;
import java.util.List;


public class PicAdapter extends RecyclerView.Adapter<PicAdapter.VH> {

    Context mContext;

    //    List<InfoBean> infoBeanList;
    private ArrayList<String> images;

    public PicAdapter(Context context, ArrayList<String> infoBeanList) {
        this.mContext = context;
//        this.infoBeanList = infoBeanList;
        this.images = infoBeanList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pic_rvhome, viewGroup, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, final int i) {
//        vh.tv_info.setText(infoBeanList.get(i).getTv_info());
        Glide.with(mContext).load(images.get(i)).centerCrop().placeholder(R.mipmap.ic_launcher).into(vh.iv_infopic);
        vh.cl_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(vh.cl_content, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder {
        //        public final TextView tv_info;
        public final ImageView iv_infopic;
        public final ConstraintLayout cl_content;

        public VH(View v) {
            super(v);
            iv_infopic = v.findViewById(R.id.iv_infopic);
            cl_content = v.findViewById(R.id.cl_content);
//            tv_info = v.findViewById(R.id.tv_info);
        }
    }
}
