package com.yfengtech.guide.display

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import com.yfengtech.guide.AppGuide
import com.yfengtech.guide.R
import com.yfengtech.guide.config.GuideGravity
import kotlinx.android.synthetic.main.activity_guide.*

/**
 * Demo
 * 演示 引导页 只显示一次或每次都显示 的功能
 *
 * @created yfengtech
 * @date 2019-07-17 11:34
 */
class GuideOnlyFirstActivity : AppCompatActivity() {

    /**
     * 通过此字段来判断
     */
    var onlyFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        rb1.isChecked = true

        rg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb1 -> onlyFirst = true
                R.id.rb2 -> onlyFirst = false
            }
        }

        buttonShowGuide.setOnClickListener {
            AppGuide.with(this)
                .startGuide {
                    scene {
                        action = "action1"
                        isOnlyFirst = false
                        highLightingElement {
                            anchorViewIdArray = arrayOf(R.id.btnHighLight)
                            textExplanation(R.id.btnHighLight) {
                                text = "只用文字解释说明一下这个按钮"
                                textStyle = R.style.MyTextStyle
                                guideGravity = GuideGravity.CENTER_HORIZONTAL or GuideGravity.BOTTOM
                                yOffset = 20
                            }
                        }
                        onGuideSceneClick {
                            AppGuide.with(this@GuideOnlyFirstActivity).showNext()
                        }
                    }
                    scene {
                        action = "action2"
                        isOnlyFirst = false
                        highLightingElement {
                            anchorViewIdArray = arrayOf(R.id.btnHighLight2)
                            imageExplanation(R.id.btnHighLight2) {
                                imageResId = R.mipmap.img_guide
                                width = 100
                                height = 100
                                guideGravity = GuideGravity.CENTER_VERTICAL or GuideGravity.LEFT
                                xOffset = 50
                            }
                            textExplanation(R.id.btnHighLight2) {
                                text = "文字和图片一起解释说明一下这个按钮"
                                textStyle = R.style.MyTextStyle
                                guideGravity = GuideGravity.CENTER_HORIZONTAL or GuideGravity.BOTTOM
                                yOffset = 20
                            }
                        }
                        onGuideSceneClick {
                            AppGuide.with(this@GuideOnlyFirstActivity).showNext()
                        }
                    }

                    scene {
                        action = "action3"
                        isOnlyFirst = false
                        highLightingElement {
                            anchorViewIdArray = arrayOf(R.id.btnHighLight3)
                            viewExplanation(R.id.btnHighLight3) {
                                width = 100
                                height = 100
                                guideGravity = GuideGravity.BOTTOM or GuideGravity.LEFT
                                explanationView = ProgressBar(this@GuideOnlyFirstActivity)
                            }
                            textExplanation(R.id.btnHighLight3) {
                                text = "文字和自定义View一起解释说明一下这个按钮"
                                textStyle = R.style.MyTextStyle
                                guideGravity = GuideGravity.CENTER_HORIZONTAL or GuideGravity.BOTTOM
                                yOffset = 20
                            }
                        }
                        onGuideSceneClick {
                            AppGuide.with(this@GuideOnlyFirstActivity).showNext()
                        }
                    }
                }
        }
    }
}
