package com.gcu.base.view.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gcu.base.network.ApiException
import com.gcu.base.network.ResponseListResult
import com.gcu.base.network.ResponseResult
import com.gcu.common.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import java.lang.Exception

open class BaseViewModel : ViewModel() {

    private val mErrorLiveData = MutableLiveData<String>()
    private val mEmptyLiveData = MutableLiveData<String>()
    private val mLoadingLiveData = MutableLiveData<Boolean>()

    fun getErrorLiveData(): MutableLiveData<String> {
        return mErrorLiveData
    }

    fun getEmptyLiveData(): MutableLiveData<String> {
        return mEmptyLiveData
    }

    fun getLoadingLiveData(): MutableLiveData<Boolean> {
        return mLoadingLiveData
    }

    fun showEmpty(emptyMsg: String) {
        mEmptyLiveData.postValue(emptyMsg)
    }

    fun showLoading() {
        mLoadingLiveData.postValue(true)
    }

    fun showError(errMsg: String) {
        mErrorLiveData.postValue(errMsg)
    }

    suspend inline fun <T> apiCall(crossinline call: suspend CoroutineScope.() -> ResponseResult<T>): ResponseResult<T> {
        return try {
            withContext(Dispatchers.IO) {
                val res: ResponseResult<T>
                try {
                    res = call()
                } catch (e: Throwable) {
                    LogUtil.e("request error $e")
                    //请求出错，将状态码和消息封装为ResponseResult
                    return@withContext ApiException.build(e).toResponse<T>()
                }
                if (res.errorCode == ApiException.CODE_AUTH_INVALID) {
                    //登陆过期，取消携程，跳转登陆界面
                    cancel()
                }
                return@withContext res
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ApiException.build(e).toResponse()
        }
    }

    suspend inline fun <T> asyncApiCall(
        crossinline call: suspend CoroutineScope.() -> ResponseResult<T>,
        crossinline listCall: suspend CoroutineScope.() -> ResponseResult<ResponseListResult<T>>
    ): Pair<ResponseResult<T>, ResponseResult<ResponseListResult<T>>> {
        return try {
            withContext(Dispatchers.IO) {
                val resPair: Pair<ResponseResult<T>, ResponseResult<ResponseListResult<T>>>
                try {
                    val res = async {
                        call()
                    }
                    val listRes = async {
                        listCall()
                    }
                    resPair = Pair(res.await(), listRes.await())
                } catch (e: Throwable) {
                    LogUtil.e("request error $e")
                    //请求出错，将状态码和消息封装为ResponseResult
                    return@withContext Pair(
                        ApiException.build(e).toResponse<T>(),
                        ApiException.build(e).toResponse<ResponseListResult<T>>()
                    )
                }
                return@withContext resPair
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Pair(
                ApiException.build(e).toResponse(),
                ApiException.build(e).toResponse()
            )
        }
    }

    fun <T> checkResponse(responseResult: ResponseResult<T>): Boolean {
        if (responseResult.errorCode == 0 && responseResult.data != null) {
            return true
        }
        return false
    }

    fun <T> checkListResponse(responseResult: ResponseResult<ResponseListResult<List<T>>>): Boolean {
        if (responseResult.errorCode == 0 && responseResult.data != null && responseResult.data!!.datas.isNotEmpty()) {
            return true
        }
        return false
    }
}