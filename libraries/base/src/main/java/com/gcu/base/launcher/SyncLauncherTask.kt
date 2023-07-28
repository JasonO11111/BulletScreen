package com.gcu.base.launcher

/**
 * 同步任务
 * Sync launcher task
 *
 * @constructor Create empty Sync launcher task
 */
abstract class SyncLauncherTask : LauncherTask {

    override fun sync(): Boolean {
        return true
    }

    override fun level(): Int {
        return 0
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun asyncTaskName(): String {
        return ""
    }
}