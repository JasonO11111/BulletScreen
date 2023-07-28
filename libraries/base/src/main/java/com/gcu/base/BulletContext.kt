package com.gcu.base

import android.app.Application

/**
 *
 * @author: zoulongsheng
 * @date: 2023/5/8
 */
object BulletContext {

    private var mIApplication: IApplication? = null

    fun init(iApplication: IApplication) {
        mIApplication = iApplication
    }

    fun getApplication(): Application {
        return mIApplication?.getApplication()!!
    }
}