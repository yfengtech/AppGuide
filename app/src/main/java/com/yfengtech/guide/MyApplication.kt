package com.yfengtech.guide

import android.app.Application
import android.widget.Toast
import com.yfengtech.guide.display.*
import com.yfengtech.guide.function.CustomGuideFragment
import com.yfengtech.guide.function.GuideBackCallbackFragment
import com.yfengtech.guide.function.GuideGravityFragment
import com.yfengtech.guide.function.LifeCycleFragment
import com.yf.smarttemplate.SmartTemplate

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SmartTemplate.init(this) {
            executionItem {
                title = "清除全部首次引导标记"
                execute {
                    AppGuide.clearGuideFirstSign(it)
                    Toast.makeText(it, "已清除首次引导标记", Toast.LENGTH_LONG).show()
                }

            }
            activityItem(GuideSampleActivity::class.java){
                title = "演示Demo"
            }
            itemList {
                title = "效果演示"
                desc = "可以从中找到你需要的效果，然后根据demo实现"
                fragmentItem(MultipleHighLightingFragment::class.java) {
                    title = "我要高亮几个区域"
                    desc = "可以同时显示多区域高亮的引导"
                }
                activityItem(FullScreenGuideActivity::class.java) {
                    title = "全屏界面显示"
                    desc = "演示全屏情况"
                }
                fragmentItem(SequentialImageGuideFragment::class.java) {
                    title = "连续展示几张图片"
                    desc = "多张图片需要依次展示"
                }
                activityItem(GuideOnlyFirstActivity::class.java) {
                    title = "配置首次展示"
                    desc = "可以依次显示高亮区域的引导"
                }
                fragmentItem(GuideChangeBgColorFragment::class.java) {
                    title = "修改引导页属性"
                    desc = "修改相关配置"
                }
            }
            itemList {
                title = "配置演示"
                desc = "可以从中找到你需要使用的功能，然后根据demo实现"
                fragmentItem(GuideBackCallbackFragment::class.java) {
                    title = "我要监听引导页的点击和返回键"
                    desc = "设置监听"
                }
                fragmentItem(GuideGravityFragment::class.java) {
                    title = "我要改变引导控件的显示位置"
                    desc = "通过设置Gravity使引导控件显示在不同的位置上"
                }
                fragmentItem(LifeCycleFragment::class.java) {
                    title = "我要监听引导周期回调，测试中断继续的功能"
                    desc = "引导页可以依次回调show和dismiss"
                }
                fragmentItem(CustomGuideFragment::class.java) {
                    title = "演示引导页代码的抽象"
                    desc = "为了逻辑代码的整洁，建议将引导页抽离单独写，可以参考此代码"
                }
            }
        }
    }
}