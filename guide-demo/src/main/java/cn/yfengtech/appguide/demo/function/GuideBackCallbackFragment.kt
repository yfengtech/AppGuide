package cn.yfengtech.appguide.demo.function

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.yfengtech.guide.AppGuide
import cn.yfengtech.guide.OnGuideBackClickListener
import cn.yfengtech.appguide.demo.R
import kotlinx.android.synthetic.main.fragment_guide_back.*

/**
 * Demo
 * 演示 返回键监听和引导页点击监听
 *
 * @created yfengtech
 * @date 2019-07-17 11:01
 */
class GuideBackCallbackFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_guide_back, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBoxBackClick.isChecked = true

        button.setOnClickListener {
            AppGuide.with(this)
                .setOnGuideBackClickListener(object : OnGuideBackClickListener {
                    /**
                     * 返回键监听
                     */
                    override fun onBackClick() {
                        if (checkBoxBackClick.isChecked) {
                            AppGuide.with(context!!).showNext()
                        }
                    }
                })
                .startGuide {
                    scene {
                        action = "GuideBackCallbackFragment"
                        isOnlyFirst = false
                        highLightingElement {
                            anchorViewIdArray = arrayOf(R.id.imageView)
                            textExplanation(R.id.imageView) {
                                text = "这是个图片"
                                textStyle = R.style.MyTextStyle
                            }
                        }
                        /**
                         * 当前引导页点击的监听
                         */
                        onGuideSceneClick {
                            if (checkBoxGuideClick.isChecked) {
                                AppGuide.with(this@GuideBackCallbackFragment).showNext()
                            }
                        }
                    }
                }
        }
    }
}