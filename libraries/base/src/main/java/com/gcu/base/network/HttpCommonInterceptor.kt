package com.gcu.base.network

import okhttp3.Interceptor
import okhttp3.Response

class HttpCommonInterceptor(private val mHeaderParamsMap: MutableMap<String, String>) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldReq = chain.request()
        val newReqBuilder = oldReq.newBuilder()
        newReqBuilder.method(oldReq.method, oldReq.body)
        if (mHeaderParamsMap.isNotEmpty()) {
            for ((key, value) in mHeaderParamsMap) {
                newReqBuilder.header(key, value)
            }
        }
        val newReq = newReqBuilder.build()
        return chain.proceed(newReq)
    }
}