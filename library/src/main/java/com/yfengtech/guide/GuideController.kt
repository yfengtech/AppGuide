package com.yfengtech.guide

import android.content.Context
import android.content.res.Resources
import android.graphics.PixelFormat
import android.view.WindowManager
import com.yfengtech.guide.config.Guide
import com.yfengtech.guide.config.GuideScene
import com.yfengtech.guide.config.HighLightingElement
import com.yfengtech.guide.config.ViewElement
import com.yfengtech.guide.view.GuideLayout
import com.yfengtech.guide.view.GuideWindow
import java.lang.IllegalArgumentException

/**
 * 引导的控制器，直接控制引导页的显示、隐藏
 *
 * @created yfengtech
 * @date 2019-07-11 19:39
 */

class GuideController(private val context: Context, private val windowManager: WindowManager) {

    private var onGuideChangeListener: OnGuideChangeListener? = null

    private var onGuideBackClickListener: OnGuideBackClickListener? = null

    private var guideSceneList: ArrayList<GuideScene> = arrayListOf()

    /**
     * 引导页 window的跟布局
     */
    private var guideWindow = GuideWindow(
        context,
        object : GuideWindow.OnWindowClickListener {
            override fun onClickBack() {
                if (onGuideBackClickListener == null) {
                    showNext()
                } else {
                    onGuideBackClickListener?.onBackClick()
                }
            }
        })

    /**
     * DSL配置的方式，开始展示引导页，可以包含多个引导
     */
    fun startGuide(guide: Guide.() -> Unit) {
        val g = Guide().apply(guide)
        startGuide(g)
    }

    /**
     * 以直接传入配置好的对象的方式，开始展示引导页，可以包含多个引导
     */
    fun startGuide(guide: Guide) {
        if (guide.sceneList.size > 0) {
            // 有引导页场景，创建window
            guideSceneList.addAll(guide.sceneList)
        }
        showNext()
    }

    /**
     * DSL的方式显示一个引导页
     */
    fun startGuideScene(guideScene: GuideScene.() -> Unit) {
        val s = GuideScene.create(guideScene)
        startGuideScene(s)
    }

    /**
     * 直接显示一个引导页
     */
    fun startGuideScene(guideScene: GuideScene) {
        guideSceneList.clear()
        guideSceneList.add(guideScene)
        showNext()
    }

    /**
     * 设置监听引导页显示隐藏变化的监听器
     */
    fun setOnGuideChangeListener(listener: OnGuideChangeListener?): GuideController {
        onGuideChangeListener = listener
        return this
    }

    /**
     * 设置监听引导页返回键的监听器
     */
    fun setOnGuideBackClickListener(listener: OnGuideBackClickListener?): GuideController {
        onGuideBackClickListener = listener
        return this
    }

    /**
     * 显示下一个scene
     *
     * @param isSkipShowedScene 自动跳过已经展示过的引导页，展示下一个
     *
     * @return true 显示成功  false 显示失败，没有可显示的了
     */
    fun showNext(): Boolean {
        if (guideSceneList.size > 0) {
            val scene = guideSceneList[0]
            checkParams(scene)
            // 判断是 如果是首次展示  或者  忽略首次标记的 显示引导
            if (Utils.isFirstGuide(context, scene.action!!) || !scene.isOnlyFirst) {
                Utils.setGuideShowed(context, scene.action!!)
                showWindow()
                removeAllShowingScene()
                showScene(guideSceneList[0])
                return true
            } else {
                // 跳过这个引导页，并从列表中移除
                guideSceneList.remove(scene)
                return showNext()
            }
        } else {
            removeWindow()
            return false
        }
    }

    /**
     * 移除当前引导页，不清空列表，可以等待再次调用showNext()
     *
     */
    fun dismissCurrent() {
        removeWindow()
    }

    /**
     * 移除全部引导
     */
    fun removeAll() {
        removeWindow()
        guideSceneList.clear()
    }

    private fun showScene(guideScene: GuideScene) {
        // 检查参数
        checkParams(guideScene)
        // 1. 先移除之前的场景
        removeAllShowingScene()
        // 2.添加当前场景View
        val guideLayout = GuideLayout(context, guideScene)
        guideLayout.tag = guideScene.action
        // 3.回调 show 监听
        onGuideChangeListener?.onGuideShow(guideScene.action!!)
        guideLayout.setOnClickListener {
            guideScene.onGuideClickListener?.onGuideClick()
        }
        guideWindow.addView(guideLayout, guideScene.lparams)
        // 4.该scene从列表中移除
        guideSceneList.remove(guideScene)
    }

    /**
     * 移除guide window
     */
    private fun removeWindow() {
        if (guideWindow.parent != null) {
            removeAllShowingScene()
            windowManager.removeViewImmediate(guideWindow)
        }
    }

    /**
     * 检查window是否需要显示
     */
    private fun showWindow() {
        if (guideWindow.parent == null) {
            // 用来判断，只添加一个guide window
            windowManager.addView(guideWindow,
                WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION).apply {
                    format = PixelFormat.RGBA_8888
                    width = Resources.getSystem().displayMetrics.widthPixels
                    height = Resources.getSystem().displayMetrics.heightPixels
                })
        }
    }

    /**
     * 移除所有显示中的scene，但不移除内存中的
     */
    private fun removeAllShowingScene() {
        // 移除所有子view并回调监听
        if (guideWindow.childCount > 0) {
            for (i in 0 until guideWindow.childCount) {
                val view = guideWindow.getChildAt(i)
                (view.tag as? String)?.let {
                    onGuideChangeListener?.onGuideDismiss(it)
                }
            }
            guideWindow.removeAllViews()
        }
    }

    /**
     * 对即将要显示的引导页，进行参数检查
     */
    private fun checkParams(guideScene: GuideScene) {
        if (guideScene.action.isNullOrBlank()) {
            throw IllegalArgumentException("GuideScene action must not be null")
        }
        if (guideScene.elementList.size == 0) {
            throw IllegalArgumentException("GuideScene 内部元素element must not be null")
        }
        guideScene.elementList.forEach {
            when (it) {
                is ViewElement -> {
                    if (it.view == null) {
                        throw IllegalArgumentException("element's view must not be null")
                    }
                }
                is HighLightingElement -> {
                    if (it.anchorViewIdArray == null || it.anchorViewIdArray!!.isEmpty()) {
                        throw IllegalArgumentException("element's anchorViewArray must not be null")
                    }
                }
            }
        }
    }
}