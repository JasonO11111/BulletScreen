package com.gcu.common.utils

import android.os.Handler
import android.os.Looper

/**
 * 后台工具类
 */
object BackgroundTaskUtil {

    private val mHandler = Handler(Looper.getMainLooper())

    /**
     * ui线程
     *
     * @param runnable
     */
    fun runOnUiThread(runnable: Runnable) {
        mHandler.post(runnable)
    }

    /**
     * 延迟执行
     *
     * @param runnable
     * @param delayMillis
     */
    fun postDelayed(runnable: Runnable, delayMillis: Long) {
        mHandler.postDelayed(runnable, delayMillis)
    }

}