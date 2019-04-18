package com.opera.meitu.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.opera.meitu.R;
import com.opera.meitu.utils.ToastUtil;
import com.opera.meitu.widget.LoadingDialog;

public abstract class BaseActivity extends AppCompatActivity implements IBaseView {


    //    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);
    private LoadingDialog loadingDialog;
    private ToastUtil mToastUtil;

    private Toolbar toolBar;
    private ImageView mainIcon, subIcon, backPressed, bottomLine;
    private TextView mainTitle, subTitle;

    private boolean isForeground = true;//界面处于前台？

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && TextUtils.equals(Intent.ACTION_MAIN, action)) {
                    finish();
                    return;
                }
            }
        }

        setContentView(getLayoutId());

        if (showToolBar()) {
            initTitleBar();
        }

        initView();
        initData();
        initToolBar();
        initListeners();
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == 0)
            throw new RuntimeException("layoutResID==-1 have u create your layout?");
        if (showToolBar() && getToolBarResId() != -1) {
            // 根布局
            View rootView = LayoutInflater.from(this).inflate(R.layout.layout_base, null, false);
            // toolbar容器
            ViewStub viewStub = rootView.findViewById(R.id.vs_toolbar);
            // 子布局容器
            FrameLayout frameLayout = rootView.findViewById(R.id.base_fl_container);
            // toolbar资源id
            viewStub.setLayoutResource(getToolBarResId());
            // 填充toolbar
            viewStub.inflate();
            // 子布局
            LayoutInflater.from(this).inflate(layoutResID, frameLayout, true);
            setContentView(rootView);
        } else {
            // 不显示通用tool bar
            super.setContentView(layoutResID);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != toolBar && isShowBack()) {
            showBack();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mToastUtil != null)
            mToastUtil.cancelToast();

        super.onDestroy();
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     */
    protected boolean isShowBack() {
        return true;
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        backPressed.setVisibility(View.VISIBLE);
        backPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 获取contentView 资源id
     */
    protected abstract int getLayoutId();

    /**
     * 是否显示通用toolBar
     */
    public boolean showToolBar() {
        return true;
    }

    private void initTitleBar() {
        AppBarLayout appBarLayout = findViewById(R.id.appbar_layout);
        if (appBarLayout != null) {

            toolBar = appBarLayout.findViewById(R.id.toolbar);
            mainTitle = appBarLayout.findViewById(R.id.tb_main_title);
            subTitle = appBarLayout.findViewById(R.id.home_btn_upper);
            mainIcon = appBarLayout.findViewById(R.id.tb_main_icon);
            subIcon = appBarLayout.findViewById(R.id.tb_sub_icon);
            backPressed = appBarLayout.findViewById(R.id.tb_back_pressed);
            bottomLine = appBarLayout.findViewById(R.id.bottom_line);

            if (toolBar != null) {
                toolBar.setTitle("");
                setSupportActionBar(toolBar);
            }
        }
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initToolBar();

    protected abstract void initListeners();

    /**
     * 获取自定义tool bar view 资源id 默认为-1，showToolBar()方法必须返回true才有效
     */
    private int getToolBarResId() {
        return R.layout.layout_app_toolbar;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
    }

    /**
     * loading dialog show
     */
    public void showLoadingDialog() {
        try {
            if (loadingDialog == null)
                loadingDialog = new LoadingDialog(this);
            loadingDialog.show();
        } catch (Exception ignored) {
        }
    }

    /**
     * loading dialog dismiss
     */
    public void dismissLoadingDialog() {
        try {
            if (loadingDialog == null)
                loadingDialog = new LoadingDialog(this);
            loadingDialog.dismiss();
        } catch (Exception ignored) {
        }
    }

    /**
     * toast
     */
    public void showToast(String text) {
        if (mToastUtil == null)
            mToastUtil = new ToastUtil();

        mToastUtil.showToast(this, text);
    }

    /**
     * 获取页面title view
     *
     * @return mainTitle
     */
    public TextView getMainTitle() {
        return mainTitle != null ? mainTitle : (TextView) findViewById(R.id.tb_main_title);
    }


    /**
     * 设置页面title text
     *
     * @param title
     */
    public void setMainTitle(String title) {
        if (mainTitle != null) {
            mainTitle.setVisibility(View.VISIBLE);
            mainTitle.setText(title);
        } else {
        }
    }


    /**
     * 获取页面sub title view
     *
     * @return subTitle
     */
    public TextView getSubTitle() {
        return subTitle != null ? subTitle : (TextView) findViewById(R.id.home_btn_upper);
    }


    /**
     * 设置页面sub title text
     *
     * @param title
     */
    public void setSubTitle(String title) {
        if (subTitle != null) {
            subTitle.setVisibility(View.VISIBLE);
            subTitle.setText(title);
        } else {
        }
    }


    /**
     * 获取页面main icon view
     *
     * @return mainIcon
     */
    public ImageView getMainIcon() {
        return mainIcon != null ? mainIcon : (ImageView) findViewById(R.id.tb_main_icon);
    }


    /**
     * 设置页面sub title text
     *
     * @param resId
     */
    public void setMainIcon(int resId) {
        if (mainIcon != null) {
            mainIcon.setVisibility(View.VISIBLE);
            mainIcon.setBackgroundResource(resId);
        } else {
        }
    }


    /**
     * 获取页面sub icon view
     *
     * @return mainIcon
     */
    public ImageView getSubIcon() {
        return subIcon != null ? subIcon : (ImageView) findViewById(R.id.tb_sub_icon);
    }


    /**
     * 设置页面sub title text
     *
     * @param resId
     */
    public void setSubIcon(int resId) {
        if (subIcon != null) {
            subIcon.setVisibility(View.VISIBLE);
            subIcon.setBackgroundResource(resId);
        } else {
        }
    }


    /**
     * 设置页面返回按钮背景
     *
     * @param resId
     */
    public void setBackPressedIcon(int resId) {
        if (backPressed != null) {
            bottomLine.setVisibility(View.INVISIBLE);
            backPressed.setVisibility(View.VISIBLE);
            backPressed.setImageResource(resId);
        } else {
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean isAlive() {
        return this != null && !this.isFinishing() && !this.isDestroyed();
    }

    /**
     * 判断某个界面是否在前台
     */
    public boolean isActivityForeground() {
        return isForeground;
    }

}
