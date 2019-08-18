package com.yfengtech.guide.display

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yfengtech.guide.AppGuide
import com.yfengtech.guide.R
import com.yfengtech.guide.config.GuideGravity
import kotlinx.android.synthetic.main.fragment_multiple_light.*

/**
 * Demo
 * 多区域同时高亮演示
 */
class MultipleHighLightingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_multiple_light, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            AppGuide.with(this)
                .startGuide {
                    scene {
                        action = "multipleHighLightingScene"
                        isOnlyFirst = false
                        highLightingElement {
                            anchorViewIdArray = arrayOf(
                                R.id.button,
                                R.id.imageView,
                                R.id.progressBar
                            )
                            textExplanation(R.id.button) {
                                text = "这是个按钮"
                                textStyle = R.style.MyTextStyle
                            }
                            textExplanation(R.id.imageView) {
                                text = "这是个图片"
                                textStyle = R.style.MyTextStyle
                            }
                            textExplanation(R.id.progressBar) {
                                text = "这是个进度"
                                textStyle = R.style.MyTextStyle
                                guideGravity = GuideGravity.CENTER_HORIZONTAL or GuideGravity.BOTTOM
                            }
                        }
                        onGuideSceneClick {
                            AppGuide.with(context!!).showNext()
                        }
                    }
                }
        }
    }
}