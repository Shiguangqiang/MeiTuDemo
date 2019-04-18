package com.opera.meitu.network;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.opera.meitu.base.BaseActivity;
import com.opera.meitu.base.BaseResult;
import com.opera.meitu.base.IBasePresenter;
import com.opera.meitu.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


public abstract class BaseObserver<T> implements Observer<T> {
    public static final int SUCC = 0;
    public static final int NO_ACCESS = 2;
    public static final int SIGN_ERROR = 3;


    private IBasePresenter iBasePresenter;
    private Context context;

    //也可以用view代替
//    public BaseObserverCheckActivityStatus(IBasePresenter iBasePresenter) {
//        this.iBasePresenter = iBasePresenter;
//    }

    //也可以用view代替
    public BaseObserver(Context context, IBasePresenter iBasePresenter) {
        this.iBasePresenter = iBasePresenter;
        this.context = context;
    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onNext(T response) {

        if (!isAlive(context)) {
            LogUtil.e("err", "onNext is dead return");
            return;
        }

        BaseResult baseResult = null;
        try {
            baseResult = (BaseResult) response;
        } catch (Exception e) {
            LogUtil.e("onNext===", e.getMessage());
        }
        ApiException apiException = null;
        if (baseResult == null) {
            handleOtherException();
        } else {
            if (baseResult.getCode() == SUCC) {
                onSuccess(response);
            } else if (baseResult.getCode() == SIGN_ERROR) {
                apiException = new ApiException(ApiException.TOKEN_EXPIRED_EXCEPTION_STRING, ApiException.TOKEN_EXPIRED_EXCEPTION);
                iBasePresenter.handleException(apiException);
            } else {
                apiException = new ApiException(baseResult.getMsg(), ApiException.API_ERROR_EXCEPTION);
//                iBasePresenter.handleException(apiException);
                onAPIError(apiException);
            }
        }
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            int httpStatusCode = httpException.code();
            if (httpException.code() == 404 || httpStatusCode >= 500) {
                ApiException apiException = new ApiException(ApiException.SERVER_EXCEPTION_STRING, ApiException.SERVER_EXCEPTION);
                onAPIError(apiException);

            } else {
                handleOtherException();
            }
        } else {
            handleOtherException();
        }
    }

    @Override
    public void onComplete() {
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean isAlive(Context context) {
        BaseActivity activity = null;
        try {
            activity = (BaseActivity) context;

        } catch (Exception e) {

        }
        //Activity已经关闭，在后台，不回调
        if (activity != null && activity.isAlive() && activity.isActivityForeground())
            return true;
        else
            return false;

    }

    private void handleOtherException() {
        ApiException apiException;
        apiException = new ApiException(ApiException.OTHER_EXCEPTION_STRING, ApiException.OTHER_EXCEPTION);
        onAPIError(apiException);

    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    protected void onAPIError(ApiException apiException) {
        iBasePresenter.handleException(apiException);
    }
}
