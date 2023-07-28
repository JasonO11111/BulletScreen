package com.gcu.common.utils

import android.util.Log

object LogUtil {
    /**
     * Debug
     */
    var debug = true

    var TAG = "GCUTools Log --> "

    fun d(msg: String) {
        if (debug) {
            Log.d(TAG, msg)
        }
    }

    fun e(msg: String) {
        if (debug) {
            Log.e(TAG, msg)
        }
    }

    fun v(msg: String) {
        if (debug) {
            Log.v(TAG, msg)
        }
    }

    fun w(msg: String) {
        if (debug) {
            Log.w(TAG, msg)
        }
    }

    fun i(msg: String) {
        if (debug) {
            Log.i(TAG, msg)
        }
    }
}