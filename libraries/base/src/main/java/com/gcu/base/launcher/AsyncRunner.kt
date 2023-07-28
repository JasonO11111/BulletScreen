package com.gcu.base.launcher

import android.app.Application
import android.os.AsyncTask

class AsyncRunner(
    private val application: Application,
    private val tasks: MutableList<LauncherTask>
) : AsyncTask<Unit, Unit, Unit>() {

    override fun doInBackground(vararg params: Unit?) {
        //任务根据优先级进行排序
        tasks.sortBy {
            it.level()
        }
        tasks.forEach {
            try {
                it.init(application)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

}