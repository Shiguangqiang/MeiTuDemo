package com.opera.meitu.mvp.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.ielse.imagewatcher.ImageWatcher;
import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.google.android.flexbox.FlexLine;
import com.google.android.flexbox.FlexboxLayout;
import com.opera.meitu.R;
import com.opera.meitu.adapter.PicAdapter;
import com.opera.meitu.adapter.RvAdapter;
import com.opera.meitu.base.MvpBaseActivity;
import com.opera.meitu.bean.InfoBean;
import com.opera.meitu.mvp.imagewatcher.ImageWatcherActivity;
import com.opera.meitu.utils.GlideImageLoader;
import com.opera.meitu.utils.GlideSimpleLoader;
import com.opera.meitu.utils.Utils;
import com.opera.meitu.widget.CustomLoadingUIProvider2;
import com.opera.meitu.widget.DecorationLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
public class MainActivity extends MvpBaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener {

    InfoBean infoBean;
    private Banner mBanner;
    private ArrayList<String> images;
    private LinearLayout mMenu, mTop;
    //popup窗口里的ListView
    private ListView mTypeLv;
    // popup窗口
    private PopupWindow typeSelectPopup;
    //模拟的假数据
    private List<String> testData, flagData, picData;
    //数据适配器
    private ArrayAdapter<String> testDataAdapter;
    //recycleview
    private RecyclerView rv_list, rv_pic_list;
    private RvAdapter mRvAdapter;
    private PicAdapter mPicAdapter;
    //制造假数据
    private List<InfoBean> mInfoBeanList;
    private FlexboxLayout mFlex_layout;
    private ToggleButton mToggle_btn;
    private ImageView mIv_head;

    private ImageWatcherHelper iwHelper;
    private DecorationLayout layDecoration;
    private final SparseArray<ImageView> mVisiblePictureList = new SparseArray<>();
    private List<Uri> mDataList;

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean showToolBar() {
        return false;
    }

    @Override
    protected void initView() {
        mMenu = findViewById(R.id.ll_menu);
        mTop = findViewById(R.id.top_ll);
        mBanner = findViewById(R.id.banner);
        rv_list = findViewById(R.id.rv_list);
        mFlex_layout = findViewById(R.id.flex_layout);
        mToggle_btn = findViewById(R.id.toggle_btn);
        rv_pic_list = findViewById(R.id.pic_rv);
        mIv_head = findViewById(R.id.iv_head);
        initBanner();
        initIwHelper();

    }

    @Override
    protected void initData() {
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
                Toast.makeText(MainActivity.this, "当前点击第" + (position + 1) + "张图片", Toast.LENGTH_SHORT).show();
            }
        }).start();


        mInfoBeanList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            infoBean = new InfoBean();
            infoBean.setIv_url(images.get(i));
            infoBean.setTv_info("测试" + i);
            mInfoBeanList.add(infoBean);
            Log.e("mInfoBeanList", mInfoBeanList.toString());
        }

        mRvAdapter = new RvAdapter(this, mInfoBeanList);
        rv_list.setAdapter(mRvAdapter);
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        flagData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String str = "第" + i + "个";
            flagData.add(str);
        }

        addChildView();
        mFlex_layout.post(new Runnable() {
            //获取到数据再做处理
            @Override
            public void run() {
                //大于两行处理
                if (mFlex_layout.getFlexLines().size() > 1) {
                    //隐藏
                    int numInTopthree = 0;
                    for (int i = 0; i < 1; i++) {
                        if (mFlex_layout.getFlexLines().size() > 1) {
                            FlexLine flexLine = mFlex_layout.getFlexLines().get(i);
                            numInTopthree += flexLine.getItemCount();
                        }
                    }
                    mFlex_layout.removeViews(numInTopthree, mFlex_layout.getChildCount() - numInTopthree);

                }
            }
        });

        mPicAdapter = new PicAdapter(this, images);
        rv_pic_list.setAdapter(mPicAdapter);
        rv_pic_list.setLayoutManager(new GridLayoutManager(this, 2));



    }

    @Override
    protected void initToolBar() {
    }

    @Override
    protected void initListeners() {
        mMenu.setOnClickListener(this);
        mIv_head.setOnClickListener(this);
        mToggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                addChildView();
                if (isChecked) {
                    handleLines(mFlex_layout, false);
                } else {
                    handleLines(mFlex_layout, true);
                }
            }
        });
        mPicAdapter.setOnItemClickListener(new PicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                iwHelper.show(v, imageGroupList, urlList);

                fitsSystemWindow(MainActivity.this, layDecoration);
            }
        });
    }

    private void handleLines(final FlexboxLayout flex_layout, final Boolean flag) {
        flex_layout.post(new Runnable() {
            //获取到数据再做处理
            @Override
            public void run() {
                //大于两行处理
                if (flex_layout.getFlexLines().size() > 1) {
                    if (flag) {
                        //隐藏
                        int numInTopthree = 0;
                        for (int i = 0; i < 1; i++) {
                            if (mFlex_layout.getFlexLines().size() > 1) {
                                FlexLine flexLine = mFlex_layout.getFlexLines().get(i);
                                numInTopthree += flexLine.getItemCount();
                            }
                        }
                        flex_layout.removeViews(numInTopthree, flex_layout.getChildCount() - numInTopthree);

                    } else {
                        //展示
                    }
                }
            }
        });

    }

    private void addChildView() {
        //        填充数据
        if (flagData.size() > 0) {
            mFlex_layout.removeAllViews();
            for (int i = 0; i < flagData.size(); i++) {
                View child = View.inflate(this, R.layout.tag_item, null);
                TextView tv_info = child.findViewById(R.id.tv_info);
                tv_info.setText(flagData.get(i));
                mFlex_layout.addView(child);
            }
        }
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
    }

    private void initIwHelper() {

        boolean isTranslucentStatus = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            isTranslucentStatus = true;
        }
        layDecoration = new DecorationLayout(this);

        //  **************  动态 addView   **************
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(this) : 0) // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(new ImageWatcher.OnPictureLongPressListener() {
                    @Override
                    public void onPictureLongPress(ImageView v, Uri uri, int pos) {
                        // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
                        Toast.makeText(v.getContext().getApplicationContext(), "长按了第" + (pos + 1) + "张图片", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnStateChangedListener(new ImageWatcher.OnStateChangedListener() {
                    @Override
                    public void onStateChangeUpdate(ImageWatcher imageWatcher, ImageView clicked, int position, Uri uri, float animatedValue, int actionTag) {
                        Log.e("IW", "onStateChangeUpdate [" + position + "][" + uri + "][" + animatedValue + "][" + actionTag + "]");
                    }

                    @Override
                    public void onStateChanged(ImageWatcher imageWatcher, int position, Uri uri, int actionTag) {
                        if (actionTag == ImageWatcher.STATE_ENTER_DISPLAYING) {
                            Toast.makeText(getApplicationContext(), "点击了图片 [" + position + "]" + uri + "", Toast.LENGTH_SHORT).show();
                        } else if (actionTag == ImageWatcher.STATE_EXIT_HIDING) {
                            Toast.makeText(getApplicationContext(), "退出了查看大图", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setOtherView(layDecoration)
                .addOnPageChangeListener(layDecoration)
                .setLoadingUIProvider(new CustomLoadingUIProvider2()); // 自定义LoadingUI


        layDecoration.attachImageWatcher(iwHelper);
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
            case R.id.iv_head:
                startActivity(new Intent(this, ImageWatcherActivity.class));
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

    private void fitsSystemWindow(Activity activity, View otherView) {
        boolean adjustByRoot = false;
        final View content = activity.findViewById(android.R.id.content);
        if (content instanceof ViewGroup) {
            final View root = ((ViewGroup) content).getChildAt(0);
            if (root != null) {
                boolean fitsSystemWindows = ViewCompat.getFitsSystemWindows(root);
                if (fitsSystemWindows) {
                    otherView.setPadding(root.getPaddingLeft(), root.getPaddingTop(), root.getPaddingRight(), root.getPaddingBottom());
                    adjustByRoot = true;
                }
            }
        }
        if (!adjustByRoot) {
            ViewCompat.requestApplyInsets(otherView);
        }
    }
}