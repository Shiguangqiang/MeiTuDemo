package com.opera.meitu.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opera.meitu.utils.DensityUtil;
import com.opera.meitu.utils.ToastUtil;
import com.opera.meitu.widget.LoadingDialog;


/**
 * Created by Sineo on 2017/3/13.
 */

public abstract class BaseFragment extends Fragment implements IBaseView{
    protected View view;
    protected BaseActivity mActivity;
    private ToastUtil mToastUtil;
//    private ErrorToast mErrorToast;
    private LoadingDialog loadingDialog;

    protected int screenWidth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


//        if (this.view == null) {
//            this.view = inflater.inflate(this.getLayoutId(), container, false);
//        }
//        if (this.view.getParent() != null) {
//            ViewGroup parent = (ViewGroup) this.view.getParent();
//            parent.removeView(this.view);
//        }
        mToastUtil = new ToastUtil();
//        mErrorToast = new ErrorToast();
        loadingDialog = new LoadingDialog(mActivity);

        screenWidth = mActivity.getWindowManager().getDefaultDisplay().getWidth();
        screenWidth = screenWidth - (DensityUtil.dip2px(getActivity(), 13 * 2) + DensityUtil.dip2px(mActivity, 80));
//        this.initToolbar(savedInstanceState);
//        this.initListeners();
//        this.initData();
//        return this.view;
        return inflater.inflate(this.getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        this.initViews(view, savedInstanceState);
        this.initToolbar(savedInstanceState);
        this.initData();
        this.initListeners();
    }

    //
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        LogUtilLib.e("\n onCreate:" + this.getClass().getSimpleName());
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        LogUtilLib.e("\n onViewCreated:" + this.getClass().getSimpleName());
//        super.onViewCreated(view, savedInstanceState);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected abstract int getLayoutId();

    protected abstract void initViews(View view, Bundle savedInstanceState);

    protected abstract void initToolbar(Bundle savedInstanceState);

    protected abstract void initListeners();

    protected abstract void initData();

    public void updataFragment(){
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull Class<?> targetActivity) {
        startActivity(new Intent(getActivity(), targetActivity));
    }

    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivityForResult(@NonNull Class<?> targetActivity, int requestCode) {
        startActivityForResult(new Intent(getActivity(), targetActivity),requestCode);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param data           Activity之间传递数据，Intent的Extra key为Constant.EXTRA_NAME.DATA
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivity(@NonNull String name, @NonNull Bundle data, @NonNull Class<?> targetActivity) {
        final Intent intent = new Intent();
        intent.putExtra(name, data);
        intent.setClass(getActivity(), targetActivity);
        startActivity(intent);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param data           Activity之间传递数据，Intent的Extra key为Constant.EXTRA_NAME.DATA
     * @param targetActivity 要跳转的目标Activity
     */
    protected final void startActivityForResult(@NonNull String name, @NonNull Bundle data,
                                                @NonNull Class<?> targetActivity, int requestCode) {
        final Intent intent = new Intent();
        intent.putExtra(name, data);
        intent.setClass(getActivity(), targetActivity);
        startActivityForResult(intent,requestCode);
    }


    protected final void showErrorToast(int errorCode) {
        if (getActivity() == null) {
        return;
    }
        if(mActivity instanceof  BaseActivity){
            BaseActivity baseActivity  = mActivity;
            //baseActivity.showErrorToast(errorCode);
        }
    }

    public void showToast(String text) {
        if (mToastUtil == null) {
            mToastUtil = new ToastUtil();
        }
        mToastUtil.showToast(mActivity, text);
    }

    @Override
    public void onDestroy() {
//        mErrorToast.cancelToast();
        mToastUtil.cancelToast();
        super.onDestroy();
    }

    public void showLoadingDialog() {
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        loadingDialog.dismiss();
    }

    public boolean isAlive(){
        return mActivity!=null && !mActivity.isFinishing() && !mActivity.isDestroyed();
    }

}
