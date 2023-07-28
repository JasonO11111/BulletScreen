package com.gcu.base.launcher

import android.app.Application

class SuperLauncher(private val application: Application) {

    private val mTasks = mutableListOf<LauncherTask>()

    /**
     * 添加任务
     * @param task LauncherTask
     * @return SuperLauncher
     */
    fun add(task: LauncherTask): SuperLauncher {
        mTasks.add(task)
        return this
    }

    fun run() {
        val syncTasks = mutableListOf<LauncherTask>()
        val asyncTasks = mutableListOf<LauncherTask>()
        mTasks.forEach { task ->
            if (!isMainProcess() && task.onlyMainProcess()) {
                return@forEach
            }
            if (task.sync()) {
                syncTasks.add(task)
            } else {
                asyncTasks.add(task)
            }
        }
        runSync(syncTasks)
        runAsync(asyncTasks)
    }

    /**
     * 执行同步任务
     * @param tasks MutableList<LauncherTask>
     * @return Unit
     */
    private fun runSync(tasks: MutableList<LauncherTask>) {
        tasks.sortBy {
            it.level()
        }
        tasks.forEach { task ->
            try {
                task.init(application)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 执行异步任务
     * @param tasks MutableList<LauncherTask>
     * @return Unit
     */
    private fun runAsync(tasks: MutableList<LauncherTask>) {
        val tasksMap = hashMapOf<String, MutableList<LauncherTask>>()
        tasks.forEach { task ->
            val name = task.asyncTaskName()
            var list = tasksMap[name]
            if (list == null) {
                list = mutableListOf()
                tasksMap[name] = list
            }
            list.add(task)
        }
        tasksMap.forEach {
            val task = it.value
            AsyncRunner(application, task).execute()
        }
    }

    private fun isMainProcess(): Boolean {
        return application.packageName == ProcessUtil.getCurrentProcessName()
    }
}