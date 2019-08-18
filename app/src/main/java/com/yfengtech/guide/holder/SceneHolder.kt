package com.yfengtech.guide.holder

import android.content.Context
import com.yfengtech.guide.AppGuide
import com.yfengtech.guide.R
import com.yfengtech.guide.config.GuideGravity
import com.yfengtech.guide.config.GuideScene

object SceneHolder {

    /**
     * 获取通过fragment展示一张图片的场景
     */
    fun getImageScene2(context: Context): GuideScene {
        return Sample1GuideScene(context)
    }

    /**
     * 获取多区域同时高亮的场景
     */
    fun getMultipleHighLightingScene(context: Context, view1: Int, view2: Int, view3: Int): GuideScene {
        return GuideScene.create {
            action = "multipleHighLightingScene"
            highLightingElement {
                anchorViewIdArray = arrayOf(
                    view1,
                    view2,
                    view3
                )
                textExplanation(view1) {
                    text = "这是个按钮"
                    textStyle = R.style.MyTextStyle
                }
                textExplanation(view2) {
                    text = "这是个图片"
                    textStyle = R.style.MyTextStyle
                }
                textExplanation(view3) {
                    text = "这是个进度"
                    textStyle = R.style.MyTextStyle
                    guideGravity = GuideGravity.CENTER_HORIZONTAL or GuideGravity.BOTTOM
                }
            }
            onGuideSceneClick {
                AppGuide.with(context).showNext()
            }
        }
    }
}