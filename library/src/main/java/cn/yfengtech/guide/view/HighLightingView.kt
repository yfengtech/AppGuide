package cn.yfengtech.guide.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 高亮显示指定区域内容，其余区域半透明
 *
 * 支持同时高亮多个区域，传入一个Rect数组
 * [com.yfengtech.guide.view.HighLightingView.getHighLightingRectList]
 *
 * @created yfengtech
 * @date 2019-07-12 15:16
 */
internal class HighLightingView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val childPath = Path().apply {
        fillType = Path.FillType.WINDING
    }
    val xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)

    /**
     * 高亮区域列表
     */
    var highLightingRectList = arrayListOf<Rect>()
        set(value) {
            field = value
            invalidate()
        }


    fun setBgColor(color: Int) {
        paint.color = color
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 绘制半透明蒙层
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        // 绘制高亮区域
        childPath.reset()
        highLightingRectList.forEach { highLightingRect ->
            childPath.addRect(
                highLightingRect.left.toFloat(),
                highLightingRect.top.toFloat(),
                highLightingRect.right.toFloat(),
                highLightingRect.bottom.toFloat(),
                Path.Direction.CW
            )
        }
        paint.xfermode = xfermode
        canvas?.drawPath(childPath, paint)
        paint.xfermode = null
    }
}