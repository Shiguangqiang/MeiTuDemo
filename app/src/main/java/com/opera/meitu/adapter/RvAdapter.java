package com.opera.meitu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.opera.meitu.R;
import com.opera.meitu.bean.InfoBean;

import java.util.List;

/**
 * Created by Sgq
 * Create Date 2019/4/17 and 17:19
 * desc: 首页横向recycleview
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.VH> {

    Context mContext;
    List<InfoBean> infoBeanList;

    public RvAdapter(Context context, List<InfoBean> infoBeanList) {
        this.mContext = context;
        this.infoBeanList = infoBeanList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rvhome, viewGroup, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.tv_info.setText(infoBeanList.get(i).getTv_info());
        Glide.with(mContext).load(infoBeanList.get(i).getIv_url()).centerCrop().placeholder(R.mipmap.ic_launcher).into(vh.iv_info);
    }

    @Override
    public int getItemCount() {
        return infoBeanList.size();
    }


    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder {
        public final TextView tv_info;
        public final ImageView iv_info;

        public VH(View v) {
            super(v);
            iv_info = v.findViewById(R.id.iv_info);
            tv_info = v.findViewById(R.id.tv_info);
        }
    }
}
