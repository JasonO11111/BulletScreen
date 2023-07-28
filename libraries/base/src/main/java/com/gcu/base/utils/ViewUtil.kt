package com.gcu.common.utils

import android.content.res.Resources
import android.util.TypedValue

object ViewUtil {
    /**
     * 根据手机的分辨率将dp转成为px。
     */
    fun dp2px(dpValue: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpValue,
            Resources.getSystem().displayMetrics
        )
    }
}