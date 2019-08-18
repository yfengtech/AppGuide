package com.yfengtech.guide.config

import android.support.annotation.IdRes
import android.view.View
import android.widget.FrameLayout

/**
 * 引导页面的显示元素
 * 每个Scene为一层
 * @see [com.yfengtech.guide.config.GuideScene]
 *
 * 每个Scene内部可存在多个Element，根据添加顺序 叠加显示
 *
 * @created yfengtech
 * @date 2019-07-12 10:48
 */
abstract class Element

/**
 * 高亮元素，支持同时指定多个高亮元素，会同时显示
 */
class HighLightingElement : Element() {
    var anchorViewIdArray: Array<Int>? = null

    /**
     * 对高亮区域的说明配置
     */
    internal var explanationConfigList: ArrayList<ExplanationConfig> = arrayListOf()

    fun textExplanation(@IdRes anchorViewId: Int, config: TextExplanationConfig.() -> Unit) {
        val textConfig = TextExplanationConfig(anchorViewId).apply(config)
        if (textConfig.text.isNullOrBlank() && textConfig.textResId == null) {
            throw IllegalArgumentException("textExplanation text or textResId must not be null")
        }
        explanationConfigList.add(textConfig)
    }

    fun imageExplanation(@IdRes anchorViewId: Int, config: ImageExplanationConfig.() -> Unit) {
        val imageConfig = ImageExplanationConfig(anchorViewId).apply(config)
        if (imageConfig.imageResId == null) {
            throw IllegalArgumentException("imageExplanation imageResId must not be null")
        }
        explanationConfigList.add(imageConfig)
    }

    fun viewExplanation(@IdRes anchorViewId: Int, config: ViewExplanationConfig.() -> Unit) {
        val viewConfig = ViewExplanationConfig(anchorViewId).apply(config)
        if (viewConfig.explanationView == null) {
            throw IllegalArgumentException("viewExplanation explanationView must not be null")
        }
        // 约束布局需要id，如果没有id就生成一个
        if (viewConfig.explanationView!!.id == View.NO_ID) {
            viewConfig.explanationView!!.id = View.generateViewId()
        }
        explanationConfigList.add(viewConfig)
    }
}

/**
 * view元素，支持直接接受一个view，作为在Scene中的一层
 */
class ViewElement : Element() {
    var view: View? = null
    var lparams: FrameLayout.LayoutParams? = null
}