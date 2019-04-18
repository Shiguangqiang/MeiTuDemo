package com.opera.meitu.base

import android.content.Context
import com.opera.meitu.network.ApiException
import com.opera.meitu.utils.NetUtil


/**
 * Created by Administrator on 2018/6/28.
 */

abstract class BasePresenterImpl<V : IBaseView>(protected var context: Context, protected var view: V) :
    IBasePresenter {

    open fun handleGeneralException(apiExcpetion: ApiException) {
        view.showToast(apiExcpetion.message)
    }

    open fun handleNoNetException() {
        view.showToast(ApiException.NO_HTTP_EXCEPTION)
    }

    private fun handleTokenException() {
        NetUtil.handleTokenError(context)
    }

    final override fun handleException(apiExcpetion: ApiException?) {
        view.dismissLoadingDialog()

        if (!NetUtil.isNetConnected(context)) {
            handleNoNetException()
            return
        }

        if (apiExcpetion == null)
            return;

        when (apiExcpetion?.code) {

            ApiException.SERVER_EXCEPTION, ApiException.OTHER_EXCEPTION, ApiException.API_ERROR_EXCEPTION -> handleGeneralException(
                apiExcpetion!!
            )
            ApiException.TOKEN_EXPIRED_EXCEPTION -> handleTokenException()


            else -> {
            }
        }

    }

}
