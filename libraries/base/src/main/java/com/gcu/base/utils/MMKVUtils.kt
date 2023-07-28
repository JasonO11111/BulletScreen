package com.gcu.base.utils

import com.tencent.mmkv.MMKV

object MMKVUtils {

    private val mmkv = MMKV.defaultMMKV()

    fun putBoolean(key: String, value: Boolean) {
        mmkv.putBoolean(key, value)
    }

    fun getBoolean(key: String): Boolean {
        return mmkv.getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return mmkv.getBoolean(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        mmkv.putString(key, value)
    }

    fun getString(key: String): String? {
        return mmkv.getString(key, "")
    }

    fun putInt(key: String, value: Int) {
        mmkv.putInt(key, value)
    }

    fun getInt(key: String): Int {
        return mmkv.getInt(key, 0)
    }

}