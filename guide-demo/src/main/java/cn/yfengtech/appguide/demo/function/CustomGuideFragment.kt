package cn.yfengtech.appguide.demo.function

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.yfengtech.guide.AppGuide
import cn.yfengtech.appguide.demo.R
import cn.yfengtech.appguide.demo.holder.ImageGuide
import cn.yfengtech.appguide.demo.holder.OcrImageGuideScene
import kotlinx.android.synthetic.main.fragment_custom_guide.*

/**
 * Demo
 *
 * 展示提前封装好的引导
 */
class CustomGuideFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_custom_guide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            AppGuide.with(this)
                .startGuideScene(OcrImageGuideScene(context!!))
        }

        button2.setOnClickListener {
            AppGuide.with(this)
                .startGuide(ImageGuide(context!!))
        }
    }
}