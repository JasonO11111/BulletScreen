package com.gcu.bulletscreen.app

import android.app.Application
import com.gcu.base.BulletContext
import com.gcu.base.IApplication

/**
 *
 * @author: zoulongsheng
 * @date: 2023/7/27
 */
class BulletApplication : Application(), IApplication {

    override fun onCreate() {
        super.onCreate()
        BulletContext.init(this)
    }

    override fun getApplication(): Application {
        return this
    }
}