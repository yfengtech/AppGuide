package com.yfengtech.guide.config

import android.graphics.Color
import android.widget.FrameLayout
import com.yfengtech.guide.OnGuideClickListener

/**
 * 引导页的场景，内部所有元素都会同时显示
 */
open class GuideScene {
    var action: String? = null
    var isOnlyFirst:Boolean = true
    /**
     * 该场景下的背景颜色
     */
    var bgColor: Int = Color.parseColor("#bb000000")

    internal var onGuideClickListener: OnGuideClickListener? = null

    var lparams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT
    )

    /**
     * element 列表
     */
    internal val elementList: ArrayList<Element> = ArrayList()

    fun viewElement(element: ViewElement.() -> Unit) {
        elementList.add(ViewElement().apply(element))
    }

    fun highLightingElement(element: HighLightingElement.() -> Unit) {
        elementList.add(HighLightingElement().apply(element))
    }

    fun onGuideSceneClick(listener: () -> Unit) {
        onGuideClickListener = object : OnGuideClickListener {
            override fun onGuideClick() {
                listener.invoke()
            }
        }
    }

    companion object {
        /**
         * 提供单独创建的方式，为了上层方便封装，方便调用
         */
        fun create(guideScene: GuideScene.() -> Unit): GuideScene {
            return GuideScene().apply(guideScene)
        }
    }

}


