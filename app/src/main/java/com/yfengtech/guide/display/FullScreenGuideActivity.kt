package com.yfengtech.guide.display

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.yfengtech.guide.AppGuide
import com.yfengtech.guide.R
import com.yfengtech.guide.holder.OcrImageGuideScene
import kotlinx.android.synthetic.main.activity_guide2.*

/**
 * Demo
 * 演示全屏情况下引导页
 *
 * @created yfengtech
 * @date 2019-07-17 11:23
 */
class FullScreenGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_guide_button)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        button.setOnClickListener {
            AppGuide.with(this)
                .startGuideScene(OcrImageGuideScene(this))
        }
    }
}
