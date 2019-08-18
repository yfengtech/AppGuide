package com.yfengtech.guide.display

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yfengtech.guide.AppGuide
import com.yfengtech.guide.R
import com.yfengtech.guide.holder.OcrImageGuideScene
import kotlinx.android.synthetic.main.fragment_gravity_guide.*

/**
 * Demo
 * 演示改变引导的背景颜色
 *
 * @created yfengtech
 * @date 2019-07-17 10:57
 */
class GuideChangeBgColorFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_simple_guide_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            AppGuide.with(this)
                .startGuideScene(OcrImageGuideScene(context!!).apply {
                    bgColor = Color.parseColor("#600000ff")
                })
        }
    }
}