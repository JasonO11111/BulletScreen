package com.gcu.common.utils

import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.gcu.base.BulletContext

/**
 * Toast工具类
 */
object ToastUtil {

    private var mToast: Toast? = null

    fun toastLongMessage(message: String) {
        toastMessage(message, true)
    }

    fun toastShortMessage(message: String) {
        toastMessage(message, false)
    }

    private fun toastMessage(message: String, isLong: Boolean) {
        BackgroundTaskUtil.runOnUiThread {
            if (mToast != null) {
                mToast?.cancel()
                mToast = null
            }
            mToast = Toast.makeText(
                BulletContext.getApplication(),
                message,
                if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            )
            // 解决各个手机系统 toast 文字对齐方式不一致的问题
            val view = mToast?.view
            val textView = view?.findViewById<TextView>(android.R.id.message)
            textView?.gravity = Gravity.CENTER
            mToast?.show()
        }
    }
}