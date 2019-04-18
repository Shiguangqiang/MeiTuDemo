package com.opera.meitu.mvp.main;

import android.content.Context;

import com.opera.meitu.base.BasePresenterImpl;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Sgq
 * Create Date 2019/4/18 and 17:33
 * desc: TODO
 */
public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(@NotNull Context context, @NotNull MainContract.View view) {
        super(context, view);
    }


}



