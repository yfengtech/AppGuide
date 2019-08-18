package com.yfengtech.guide.config

/**
 * 显示引导页面的配置入口，经过此配置可以依次显示多个引导页
 *
 * @created yfengtech
 * @date 2019-07-12 10:54
 */
open class Guide {
    /**
     * addScene 列表
     */
    internal val sceneList = arrayListOf<GuideScene>()

    /**
     * 传入提前创建好的scene
     */
    fun addScene(guideScene: GuideScene) {
        sceneList.add(guideScene)
    }

    /**
     * 使用DSL方式配置
     */
    fun scene(guideScene: GuideScene.() -> Unit) {
        val s = GuideScene().apply(guideScene)
        addScene(s)
    }

    companion object {
        /**
         * 提供单独创建的方式，为了上层方便封装，方便调用
         */
        fun create(guide: Guide.() -> Unit): Guide {
            return Guide().apply(guide)
        }
    }
}