package com.opera.meitu.base

import com.opera.meitu.network.ApiException


/**
 * Created by Sgq
 * Create Date 2019/4/18 and 11:47
 * desc: IBasePresenter
 */
interface IBasePresenter {

    fun handleException(apiExcpetion: ApiException?)

}