package com.yfengtech.guide.holder

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import com.yfengtech.guide.AppGuide
import com.yfengtech.guide.R
import com.yfengtech.guide.config.Guide

object GuideHolder {
    /**
     * 获取 连续显示图片的引导guide
     *
     * 这样做避免使用内部类的方式，简化逻辑页面代码
     */
    fun getSequentialImageGuide(context: Context): Guide {
        return Guide.create{

            // 添加一个图片场景1
            addScene(OcrImageGuideScene(context))

            // 添加一个图片场景2
            addScene(SceneHolder.getImageScene2(context))

            // 添加一个图片场景3
            scene {
                action = "image_scene3"
                isOnlyFirst = false
                viewElement {
                    this.view = ImageView(context).apply {
                        setImageResource(R.mipmap.img_guide3)
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
    }
}