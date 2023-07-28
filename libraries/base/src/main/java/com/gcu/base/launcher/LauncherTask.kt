package com.gcu.base.launcher

import android.app.Application

interface LauncherTask {

    /**
     * 是否同步
     * @return Boolean
     */
    fun sync(): Boolean

    /**
     * 异步
     * @return String
     */
    fun asyncTaskName(): String

    fun level(): Int

    fun onlyMainProcess(): Boolean

    fun init(application: Application)
}