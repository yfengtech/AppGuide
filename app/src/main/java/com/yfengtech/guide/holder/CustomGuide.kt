package com.yfengtech.guide.holder

import android.content.Context
import com.yfengtech.guide.config.Guide

/**
 * Demo
 *
 * 一个抽出去的引导页，这里可以包含多层引导，实例中定义了两层
 */
class ImageGuide(context: Context) : Guide() {
    init {
        addScene(OcrImageGuideScene(context))

        addScene(Sample1GuideScene(context))
    }
}