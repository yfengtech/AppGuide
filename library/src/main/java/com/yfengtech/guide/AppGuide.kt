package com.yfengtech.guide

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.support.v4.app.Fragment
import android.view.WindowManager

object AppGuide {

    /**
     * fragment 使用引导
     */
    fun with(fragment: Fragment): GuideController {
        if (fragment.context == null) {
            throw IllegalArgumentException("fragment context must not be null")
        }
        if (fragment.activity == null) {
            throw IllegalArgumentException("fragment activity must not be null")
        }
        return with(fragment.context!!, fragment.activity!!.windowManager)
    }

    /**
     * dialog 使用引导
     */
    fun with(dialog: Dialog): GuideController {
        if (dialog.window == null) {
            throw IllegalArgumentException("dialog window must not be null")
        }
        return with(dialog.context, dialog.window!!.windowManager)
    }

    /**
     * context 使用引导,支持activity
     */
    fun with(context: Context): GuideController {
        if (context !is Activity) {
            throw IllegalArgumentException("context must be activity")
        }
        return GuideHolder.initController(context, context.windowManager)
    }

    private fun with(context: Context, windowManager: WindowManager): GuideController {
        return GuideHolder.initController(context, windowManager)
    }

    fun clearGuideFirstSign(context: Context) {
        Preference(context).clear()
    }
}

/**
 * GuideController的Holder
 *
 * 每个context只能有一个controller
 *
 * 用于管理不同context的Controller
 *
 * @created yfengtech
 * @date 2019-07-15 13:55
 */
internal object GuideHolder {

    private val guideMap = hashMapOf<String, GuideController>()

    internal fun initController(context: Context, windowManager: WindowManager): GuideController {
        val key = context.hashCode().toString()
        synchronized(guideMap) {
            if (!guideMap.containsKey(key)) {
                guideMap[key] = GuideController(context, windowManager)
            }
        }
        return guideMap[key]!!
    }
}

/**
 * 引导页点击回调
 */
interface OnGuideClickListener {
    fun onGuideClick()
}

/**
 * 引导页显示时点击返回键回调
 */
interface OnGuideBackClickListener {
    fun onBackClick()
}

/**
 * 引导页变化回调
 */
interface OnGuideChangeListener {
    fun onGuideShow(action: String)

    fun onGuideDismiss(action: String)
}