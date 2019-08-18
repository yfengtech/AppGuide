package com.yfengtech.guide.function

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yfengtech.guide.AppGuide
import com.yfengtech.guide.R
import com.yfengtech.guide.config.GuideGravity
import kotlinx.android.synthetic.main.fragment_gravity_guide.*

/**
 * 展示引导页 设置控件位置的功能
 *
 * @created yfengtech
 * @date 2019-07-16 19:55
 */
class GuideGravityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gravity_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            val horGravity = when {
                rbLeft.isChecked -> GuideGravity.LEFT
                rbRight.isChecked -> GuideGravity.RIGHT
                rbCenterHor.isChecked -> GuideGravity.CENTER_HORIZONTAL
                else -> GuideGravity.CENTER_HORIZONTAL
            }
            val verGravity = when {
                rbTop.isChecked -> GuideGravity.TOP
                rbBottom.isChecked -> GuideGravity.BOTTOM
                rbCenterVer.isChecked -> GuideGravity.CENTER_VERTICAL
                else -> GuideGravity.CENTER_VERTICAL
            }

            AppGuide.with(this)
                .startGuideScene {
                    action = "GuideBackCallbackFragment"
                    isOnlyFirst = false
                    highLightingElement {
                        anchorViewIdArray = arrayOf(R.id.imageView)

                        textExplanation(R.id.imageView) {
                            xOffset = seekBarX.progress
                            yOffset = seekBarY.progress
                            text = "图片xx"
                            textStyle = R.style.MyTextStyle
                            guideGravity = horGravity or verGravity
                        }
                    }
                    onGuideSceneClick {
                        AppGuide.with(context!!).showNext()
                    }
                }

        }
    }
}