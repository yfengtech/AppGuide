package com.yfengtech.guide

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.WindowManager

internal object Utils {
    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(resources: Resources): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    fun isFirstGuide(context: Context, action: String): Boolean {
        // 默认是第一次
        return Preference(context).get(action, true)
    }

    fun setGuideShowed(context: Context, action: String) {
        Preference(context).put(action, false)
    }
}


/**
 * 根据view获取对应的区域
 */
internal fun View.getAnchorViewRect(rect: Rect) {
    var statusBarHeight = Utils.getStatusBarHeight(context.resources)
    if (context is Activity &&
        (context as Activity).window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == WindowManager.LayoutParams.FLAG_FULLSCREEN
    ) {
        statusBarHeight = 0
    }
    getGlobalVisibleRect(rect)
    rect.top -= statusBarHeight
    rect.bottom -= statusBarHeight
}

val DEBUG = true
internal inline fun <reified T : Any> T.debugLog(value: String) {
    if (DEBUG) {
        Log.d("AppGuide", this::class.java.simpleName + value)
    }
}