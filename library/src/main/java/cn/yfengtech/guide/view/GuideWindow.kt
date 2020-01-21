package cn.yfengtech.guide.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.FrameLayout

internal class GuideWindow @JvmOverloads constructor(
    context: Context,
    private val onClickBack: OnWindowClickListener? = null,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    init {
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    /**
     * 回调返回键
     */
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            onClickBack?.onClickBack()
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    internal interface OnWindowClickListener {
        fun onClickBack()
    }
}