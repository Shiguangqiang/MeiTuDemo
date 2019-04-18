package com.opera.meitu.base;

/**
 * Created by Sgq
 * Create Date 2019/4/18 and 11:47
 * desc: baseview
 */

public interface IBaseView {
    void showLoadingDialog();

    void dismissLoadingDialog();

    void showToast(String text);
}
