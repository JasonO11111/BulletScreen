package com.gcu.base.utils

import android.os.Process

object AppUtil {

    fun exitApp() {
        Process.killProcess(Process.myPid())
    }
}