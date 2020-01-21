package com.yfengtech.guide

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.yfengtech.appguide.demo.guideDemo
import com.yf.smarttemplate.SmartTemplate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SmartTemplate.apply(this, guideDemo)
    }
}
