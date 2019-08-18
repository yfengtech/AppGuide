package com.yfengtech.guide.view

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.support.annotation.IdRes
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.yfengtech.guide.config.*
import com.yfengtech.guide.getAnchorViewRect


/**
 * 每一层引导页的父容器，根据Scene添加子元素
 *
 * @see [com.yfengtech.guide.config.GuideScene]
 *
 * @created yfengtech
 * @date 2019-07-12 13:02
 */
internal class GuideLayout private constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    /**
     * 用来存放直接传入的view，为了方便使用LayoutParams直接配置
     */
    private var mCustomParentLayout: FrameLayout? = null

    init {
        id = View.generateViewId()
        // 默认背景是全透明
        setBackgroundColor(Color.TRANSPARENT)
    }

    constructor(context: Context, guideScene: GuideScene) : this(context) {
        initChildView(guideScene)
    }

    private fun initChildView(guideScene: GuideScene) {
        // 1.获取高亮元素
        val highLightingElement = getHighLightingElement(guideScene)
        if (highLightingElement != null) {
            // 2. 添加高亮元素层
            addHighLightingView(guideScene.bgColor, highLightingElement)
            if (highLightingElement.explanationConfigList.isNotEmpty()) {
                // 3.添加对高亮元素的解释说明层
                addExplanationView(highLightingElement.explanationConfigList)
            }
        } else {
            // 如果没有高亮元素，设置外部传入的背景色
            setBackgroundColor(guideScene.bgColor)
        }

        // 4.添加外部传进来的view
        guideScene.elementList.filter {
            it is ViewElement
        }.forEach {
            addCustomView((it as ViewElement))
        }
    }

    /**
     * 从场景scene中获取高亮元素，只能指定一个高亮元素，多了无法显示
     */
    private fun getHighLightingElement(guideScene: GuideScene): HighLightingElement? {
        return guideScene.elementList.firstOrNull {
            it is HighLightingElement && it.anchorViewIdArray != null && it.anchorViewIdArray!!.isNotEmpty()
        } as? HighLightingElement
    }

    /**
     * 添加 显示高亮区域的view
     *
     * @return true 添加成功 false 添加失败
     */
    private fun addHighLightingView(bgColor: Int, element: HighLightingElement) {
        /**
         * 高亮区域rect 集合
         */
        val highLightingRectLis = arrayListOf<Rect>()

        element.anchorViewIdArray!!.forEach {
            val rect = Rect()
            findViewFromContent(it).getAnchorViewRect(rect)
            highLightingRectLis.add(rect)
        }

        /**
         * 添加高亮指定控件的自定义view
         */
        if (highLightingRectLis.size > 0) {
            val highLightingView = HighLightingView(context)
            highLightingView.id = View.generateViewId()
            highLightingView.setBgColor(bgColor)
            val lparams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            highLightingView.highLightingRectList = highLightingRectLis
            addView(highLightingView, lparams)
        }
    }

    /**
     * 添加对高亮元素的解释说明 view
     */
    private fun addExplanationView(configList: ArrayList<ExplanationConfig>) {
        configList.forEach {
            val placeHolder = View(context)
            placeHolder.id = View.generateViewId()
            addPlaceHolderView(placeHolder, it.anchorViewId)

            val explanationView = generateViewFromConfig(it)
            if (explanationView != null) {
                addView(explanationView)
                calcExplanationLocation(
                    placeHolder.id,
                    explanationView.id,
                    it.guideGravity,
                    it.xOffset,
                    it.yOffset
                )
            }
        }
    }

    /**
     * 添加 高亮区域的占位 view
     */
    private fun addPlaceHolderView(placeHolder: View, @IdRes anchorViewId: Int) {

        val rect = Rect()
        findViewFromContent(anchorViewId).getAnchorViewRect(rect)

        addView(placeHolder, LayoutParams(rect.width(), rect.height()))

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            placeHolder.id,
            ConstraintSet.TOP,
            this.id,
            ConstraintSet.TOP,
            rect.top
        )
        constraintSet.connect(
            placeHolder.id,
            ConstraintSet.LEFT,
            this.id,
            ConstraintSet.LEFT,
            rect.left
        )
        constraintSet.applyTo(this)
    }

    private fun findViewFromContent(@IdRes viewId: Int): View {

        return when (context) {
            is Activity -> {
                val view: View? = (context as Activity).findViewById(viewId)
                view ?: throw Resources.NotFoundException("Resource Id = $viewId")
            }
            else -> {
                throw IllegalArgumentException("只能用于activity中")
            }
        }
    }

    /**
     * 直接添加一个View在父布局中
     *
     * 默认居中
     *
     * 如果viewElement中存在lparams，就使用传入的lparams
     */
    private fun addCustomView(viewElement: ViewElement) {
        synchronized(this) {
            // 创建一个父布局，用来添加子view
            if (mCustomParentLayout == null) {
                mCustomParentLayout = FrameLayout(context)
                mCustomParentLayout!!.id = View.generateViewId()
                addView(mCustomParentLayout, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
            }
        }

        val customView = viewElement.view!!
        if (viewElement.lparams == null) {
            mCustomParentLayout!!.addView(
                customView,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER
                )
            )
        } else {
            mCustomParentLayout!!.addView(customView, viewElement.lparams)
        }


    }

    /**
     * 根据配置 生成解释view
     */
    private fun generateViewFromConfig(config: ExplanationConfig): View? {
        return when (config) {
            is TextExplanationConfig -> {
                TextView(context).apply {
                    id = View.generateViewId()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setTextAppearance(config.textStyle)
                    }
                    config.textColor?.let { setTextColor(it) }
                    config.textSize?.let { textSize = it }
                    if (config.textResId != null && config.text == null) {
                        config.text = context.resources.getString(config.textResId!!)
                    }
                    text = config.text
                    layoutParams = LayoutParams(config.width, config.height)
                }
            }
            is ImageExplanationConfig -> {
                ImageView(context).apply {
                    id = View.generateViewId()
                    setImageResource(config.imageResId!!)
                    scaleType = config.scaleType ?: ImageView.ScaleType.CENTER_CROP
                    layoutParams = LayoutParams(config.width, config.height)
                }
            }
            is ViewExplanationConfig -> {
                config.explanationView!!.apply {
                    layoutParams = LayoutParams(config.width, config.height)
                }
            }
            else -> null
        }
    }

    /**
     * 根据配置 计算 解释内容显示的位置，并应用
     */
    private fun calcExplanationLocation(
        targetId: Int,
        viewId: Int,
        guideGravity: Int,
        xOffset: Int,
        yOffset: Int
    ) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        if (guideGravity and GuideGravity.CENTER == GuideGravity.CENTER) {
            constraintSet.centerVertically(viewId, targetId)
            constraintSet.centerHorizontally(viewId, targetId)
        } else {
            when {
                guideGravity and GuideGravity.CENTER_HORIZONTAL == GuideGravity.CENTER_HORIZONTAL -> {
                    constraintSet.centerHorizontally(viewId, targetId)
                }
                guideGravity and GuideGravity.LEFT == GuideGravity.LEFT -> {
                    constraintSet.connect(viewId, ConstraintSet.RIGHT, targetId, ConstraintSet.LEFT, xOffset)
                }
                guideGravity and GuideGravity.RIGHT == GuideGravity.RIGHT -> {
                    constraintSet.connect(viewId, ConstraintSet.LEFT, targetId, ConstraintSet.RIGHT, xOffset)
                }
            }
            when {
                guideGravity and GuideGravity.CENTER_VERTICAL == GuideGravity.CENTER_VERTICAL -> {
                    constraintSet.centerVertically(viewId, targetId)
                }
                guideGravity and GuideGravity.TOP == GuideGravity.TOP -> {
                    constraintSet.connect(viewId, ConstraintSet.BOTTOM, targetId, ConstraintSet.TOP, yOffset)
                }
                guideGravity and GuideGravity.BOTTOM == GuideGravity.BOTTOM -> {
                    constraintSet.connect(viewId, ConstraintSet.TOP, targetId, ConstraintSet.BOTTOM, yOffset)
                }
            }
        }
        constraintSet.applyTo(this)
    }
}