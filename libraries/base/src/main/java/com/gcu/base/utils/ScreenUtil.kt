package com.gcu.base.utils

import android.content.Context


object ScreenUtil {

    fun getWidthPixels(context: Context?): Int {
        if (context == null) {
            return 0
        }
        return context.resources.displayMetrics.widthPixels
    }

    fun getHeightPixels(context: Context?): Int {
        if (context == null) {
            return 0
        }
        return context.resources.displayMetrics.heightPixels
    }

}