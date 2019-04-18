package com.opera.meitu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Sgq
 * Create Date 2019/4/18 and 11:47
 * desc: MvpBaseFragment
 */

public abstract class MvpBaseFragment<P extends BasePresenterImpl> extends BaseFragment {
    protected P presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = initPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void updataFragment() {

    }

    protected abstract P initPresenter();
}
