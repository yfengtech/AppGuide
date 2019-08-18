package com.yfengtech.guide

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yfengtech.guide.config.GuideScene
import kotlinx.android.synthetic.main.activity_guide_sample.*

class GuideSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_sample)

        imageView2.post {
            AppGuide.with(this)
                .startGuideScene(scene1())
        }
    }

    private fun scene1() = GuideScene.create {
        action = "scene1"
        isOnlyFirst = false

        highLightingElement {
            anchorViewIdArray = arrayOf(R.id.imageView2)
        }
        onGuideSceneClick {
            AppGuide.with(this@GuideSampleActivity).showNext()
        }
    }
}
