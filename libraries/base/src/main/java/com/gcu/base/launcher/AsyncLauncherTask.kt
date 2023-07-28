package com.gcu.base.launcher

/**
 * 异步任务
 * Async launcher task
 *
 * @constructor Create empty Async launcher task
 */
abstract class AsyncLauncherTask : LauncherTask {

    override fun sync(): Boolean {
        return false
    }

    override fun level(): Int {
        return 0
    }

    override fun onlyMainProcess(): Boolean {
        return true
    }

    override fun asyncTaskName(): String {
        return toString()
    }
}