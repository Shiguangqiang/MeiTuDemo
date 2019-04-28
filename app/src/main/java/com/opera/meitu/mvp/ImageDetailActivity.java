package com.opera.meitu.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.opera.meitu.R;
import com.opera.meitu.mvp.main.MainActivity;
import com.opera.meitu.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

public class ImageDetailActivity extends AppCompatActivity {

    private Banner mBanner;
    private ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        initView();
        initData();
    }

    private void initView() {
        mBanner = findViewById(R.id.banner);
        initBanner();
    }

    private void initBanner() {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置轮播样式
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置是否可手动滑动轮播图
        mBanner.setViewPagerIsScroll(true);
        //设置是否自动轮播
        mBanner.isAutoPlay(true);
        //设置间隔时间 默认2000
        mBanner.setDelayTime(1500);
        //设置图片资源
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
    }

    private void initData() {
        //设置图片资源:url或本地资源
        images = new ArrayList<>();
        images.add("http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
        images.add("http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg");
        images.add("http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg");
        images.add("http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg");
        images.add("http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg");
        images.add("http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg");

        mBanner.setImages(images).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(ImageDetailActivity.this, "当前点击第" + (position + 1) + "张图片", Toast.LENGTH_SHORT).show();
            }
        }).start();
    }
}
