package com.opera.meitu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * Created by Sgq
 * Create Date 2019/4/18 and 11:47
 * desc: MvpBaseActivity
 */

public abstract class MvpBaseActivity<P extends BasePresenterImpl> extends BaseActivity {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P initPresenter();
}
