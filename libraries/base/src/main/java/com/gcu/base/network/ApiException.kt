package com.gcu.base.network

import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.adapter.rxjava.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ApiException(
    val code: Int, override val message: String?, override val cause: Throwable? = null
) : RuntimeException(message, cause) {
    companion object {
        // 网络状态码
        const val CODE_NET_ERROR = 4000
        const val CODE_TIMEOUT = 4080
        const val CODE_JSON_PARSE_ERROR = 4010
        const val CODE_SERVER_ERROR = 5000

        // 业务状态码
        const val CODE_AUTH_INVALID = 401

        fun build(e: Throwable): ApiException {
            return when (e) {
                is HttpException -> {
                    ApiException(CODE_NET_ERROR, "网络异常(${e.code()},${e.message()})")
                }
                is UnknownHostException -> {
                    ApiException(CODE_NET_ERROR, "网络连接失败，请检查后再试")
                }
                is ConnectTimeoutException, is SocketTimeoutException -> {
                    ApiException(CODE_TIMEOUT, "请求超时，请稍后再试")
                }
                is IOException -> {
                    ApiException(CODE_NET_ERROR, "网络异常(${e.message})")
                }
                is JsonParseException, is JSONException -> {
                    // Json解析失败
                    ApiException(CODE_JSON_PARSE_ERROR, "数据解析错误，请稍后再试")
                }
                else -> {
                    ApiException(CODE_SERVER_ERROR, "系统错误(${e.message})")
                }
            }
        }
    }

    fun <T> toResponse(): ResponseResult<T> {
        return ResponseResult(code, message)
    }
}