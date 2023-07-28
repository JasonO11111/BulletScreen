package com.gcu.base.launcher

import android.os.Process
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.Exception

/**
 * 进程工具类
 * Process util
 *
 * @constructor Create empty Process util
 */
object ProcessUtil {

    fun getCurrentProcessName(): String {
        return try {
            val file = File("/proc/" + Process.myPid() + "/" + "cmdline")
            val bufferedReader = BufferedReader(FileReader(file))
            val processName = bufferedReader.readLine().trim { it <= ' ' }
            bufferedReader.close()
            processName
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

}