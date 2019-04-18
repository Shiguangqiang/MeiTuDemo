package com.opera.meitu;

import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.opera.meitu.adapter.RvAdapter;
import com.opera.meitu.bean.InfoBean;
import com.opera.meitu.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    InfoBean infoBean;
    private Banner mBanner;
    private ArrayList<String> images;
    private LinearLayout mMenu, mTop;
    //popup窗口里的ListView
    private ListView mTypeLv;
    // popup窗口
    private PopupWindow typeSelectPopup;
    //模拟的假数据
    private List<String> testData;
    //数据适配器
    private ArrayAdapter<String> testDataAdapter;
    //recycleview
    private RecyclerView rv_list;
    private RvAdapter mRvAdapter;
    //制造假数据
    private List<InfoBean> mInfoBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据
        initData();
        //初始化视图
        initview();
        initBanner();
        initListener();
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

        mInfoBeanList = new ArrayList<>();


        for (int i = 0; i < 6; i++) {
            infoBean = new InfoBean();
            infoBean.setIv_url(images.get(i));
            infoBean.setTv_info("测试" + i + "=");
            mInfoBeanList.add(infoBean);
            Log.e("mInfoBeanList", mInfoBeanList.toString());
        }
    }

    private void initview() {
        mMenu = findViewById(R.id.ll_menu);
        mTop = findViewById(R.id.top_ll);
        mBanner = findViewById(R.id.banner);
        rv_list = findViewById(R.id.rv_list);


        mRvAdapter = new RvAdapter(this, mInfoBeanList);
        rv_list.setAdapter(mRvAdapter);
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void initBanner() {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
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
        mBanner.setImages(images).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(MainActivity.this, "当前点击第" + (position + 1) + "张图片", Toast.LENGTH_SHORT).show();
            }
        })
                .start();
    }

    private void initListener() {
        mMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_menu:
                initPopwindow();
                // 使用isShowing()检查popup窗口是否在显示状态
                if (typeSelectPopup != null && !typeSelectPopup.isShowing()) {
//                    typeSelectPopup.showAsDropDown(mTop, 100, 100);
                    if (Build.VERSION.SDK_INT >= 24) {
                        Rect visibleFrame = new Rect();
                        mTop.getGlobalVisibleRect(visibleFrame);
                        int height = mTop.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                        typeSelectPopup.setHeight(height);
                        typeSelectPopup.showAsDropDown(mTop, 0, 0);
                    } else {
                        typeSelectPopup.showAsDropDown(mTop, 0, 0);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void initPopwindow() {
        mTypeLv = new ListView(this);
        TestData();
        // 设置适配器
        testDataAdapter = new ArrayAdapter<String>(this, R.layout.popup_text_item, testData);
        mTypeLv.setAdapter(testDataAdapter);
        mTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = testData.get(position);
                Toast.makeText(MainActivity.this, "你点击了第" + (position + 1) + "个item", Toast.LENGTH_SHORT).show();
                typeSelectPopup.dismiss();
            }
        });
        typeSelectPopup = new PopupWindow(mTypeLv, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        typeSelectPopup.setFocusable(true);
        typeSelectPopup.setOutsideTouchable(true);
        typeSelectPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        typeSelectPopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        typeSelectPopup.setBackgroundDrawable(getResources().getDrawable(android.R.color.white));

        typeSelectPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // 关闭popup窗口
                typeSelectPopup.dismiss();
            }
        });
    }

    private void TestData() {
        testData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String str = new String("数据" + i);
            testData.add(str);
        }
    }
}