package cn.yfengtech.appguide.demo.holder

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import cn.yfengtech.appguide.demo.R
import cn.yfengtech.guide.AppGuide
import cn.yfengtech.guide.config.GuideScene

/**
 * Demo
 *
 * 可以提前在单独类里对引导进行一次封装，使代码逻辑清晰
 */
class OcrImageGuideScene(context:Context) : GuideScene(){
    init {
        action = "magic_ocr_guide"
        isOnlyFirst = false
        viewElement {
            this.view = ImageView(context).apply {
                setImageResource(R.mipmap.img_guide)
            }
            this.lparams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
        }
        onGuideSceneClick {
            AppGuide.with(context).showNext()
        }
    }
}

class Sample1GuideScene(context:Context) : GuideScene(){
    init {
        action = "image_other2"
        isOnlyFirst = false
        viewElement {
            this.view = ImageView(context).apply {
                setImageResource(R.mipmap.img_guide2)
            }
            this.lparams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
        }
        onGuideSceneClick {
            AppGuide.with(context).showNext()
        }
    }
}