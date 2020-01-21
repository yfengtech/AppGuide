package cn.yfengtech.guide.config

import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

/**
 * 对高亮区域的解释内容
 *
 * 目前支持显示 文本、图片、自定义view
 *
 * 可配置 宽高、显示位置、偏移量
 *
 * 显示位置通过[com.yfengtech.guide.config.GuideGravity]进行配置
 *
 * @param anchorView 锚点view，解释目标
 *
 * @created yfengtech
 * @date 2019-07-12 10:44
 */
abstract class ExplanationConfig(internal var anchorViewId: Int) {
    var width: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    var height: Int = ViewGroup.LayoutParams.WRAP_CONTENT

    var guideGravity: Int = GuideGravity.CENTER_HORIZONTAL or GuideGravity.BOTTOM

    var xOffset: Int = 0
    var yOffset: Int = 0
}

/**
 * 对解释目标区域进行 显示文本的配置
 */
class TextExplanationConfig(@IdRes anchorViewId: Int) : ExplanationConfig(anchorViewId) {
    var text: CharSequence? = null
    @StringRes
    var textResId: Int? = null
    var textStyle: Int = android.R.style.TextAppearance_Holo_Large
    var textColor: Int? = null
    var textSize: Float? = null
}

/**
 * 对解释目标区域进行 显示图片的配置
 */
class ImageExplanationConfig(@IdRes anchorViewId: Int) : ExplanationConfig(anchorViewId) {
    var imageResId: Int? = null
    var scaleType: ImageView.ScaleType? = null
}

/**
 * 对解释目标区域进行 显示view的配置
 */
class ViewExplanationConfig(@IdRes anchorViewId: Int) : ExplanationConfig(anchorViewId) {
    var explanationView: View? = null
}

object GuideGravity {
    const val LEFT = 0x01
    const val TOP = 0x02
    const val RIGHT = 0x04
    const val BOTTOM = 0x08

    const val CENTER_HORIZONTAL = LEFT or RIGHT
    const val CENTER_VERTICAL = TOP or BOTTOM
    const val CENTER = CENTER_HORIZONTAL or CENTER_VERTICAL
}