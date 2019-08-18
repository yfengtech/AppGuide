package com.yfengtech.guide

import android.content.Context
import android.content.SharedPreferences

/**
 * 操作SharePreference工具类
 *
 * @created yfengtech
 * @date 2019-07-15 17:43
 */
internal class Preference(private val context: Context) {

    private val prefs: SharedPreferences by lazy {
        context.applicationContext.getSharedPreferences(
            "app_guide",
            Context.MODE_PRIVATE
        )
    }

    fun <T> put(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")
        }.apply()
    }

    fun clear() = with(prefs.edit()) {
        clear().apply()
    }

    fun <T> get(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("This type of data cannot be saved!")
        }
        return res as T
    }
}