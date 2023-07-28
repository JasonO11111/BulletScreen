package com.gcu.base.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private const val DEFAULT_TIME_OUT = 5L //超时时间

    private const val DEFAULT_READ_TIME_OUT = 10L

    val mNetPlugin by lazy {
        NetworkFlipperPlugin()
    }

    private val retrofit by lazy<Retrofit> {
        val builder = OkHttpClient.Builder()
        builder.apply {
            connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
            cookieJar(CookieClass.cookieJar)
            addInterceptor(HttpCommonInterceptor(mutableMapOf<String, String>().apply {
                //添加通用头部
                put("os", "Android")
                put("app", "WanAndroid")
            }))
            addNetworkInterceptor(FlipperOkhttpInterceptor(mNetPlugin))
        }
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.apply {
            client(builder.build())
            addConverterFactory(GsonConverterFactory.create())
            baseUrl(NetworkApis.BASE_URL)
        }
        retrofitBuilder.build()
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    private val cookieJar by lazy {
        getCookie()
    }

    private fun getCookie(): CookieJar {
        return object : CookieJar {
            val cookieStore = mutableMapOf<String, List<Cookie>>()

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = cookieStore[url.host]
                return cookies ?: mutableListOf()
            }

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url.host] = cookies
            }
        }
    }

}