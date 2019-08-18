# AppGuide

## 总体设计

### 1. 引导页浮层的设计

* 使用window来实现
* 使用windowManager添加一个浮层
* 好处是：层级较高，可以监听按钮，无入侵，相对独立

### 2. 管理不同界面的引导页
* 库中维护一个map，使用context的hashcode作为key，即不同的context可以有自己的引导页列表
 
### 3. 管理单个界面内多个引导页的关系
* 在window中添加一个自己维护的ViewGroup
* 根据外部传进来的配置，在这个跟布局中显示和隐藏

### 4. 展示某个高亮控件
* 通过外部传入的id，找到当前引导页所有需要高亮的控件，在自己维护的这个ViewGroup最底层根据引导页蒙层颜色，绘制一个自定义view，来实现高亮效果

### 5. 对高亮控件的引导展示	
* 通过外部传入的具体引导配置，在布局内为每个高亮控件生成一个透明的占位View，再将引导指向这个占位View

### 6. 方便库的使用者
* 调用方式使用Kotlin的DSL特性封装，可以像配置的方式来显示引导页
* 使用`AppGuide.with(context/fragment)`的方式统一调用入口，降低学习成本

### 7. 连续调用配置
* 采用链式调用的方式，增加代码的可读性

```kotlin
AppGuide.with(this)
	.setOnlyFirst(false)
	.setOnGuideChangeListener(...)
	.setOnGuideBackClickListener(...)
	.startGuide(ImageGuide(context!!))
```

### 8. 高亮控件引导位置
* 对高亮控件引导显示的位置的定义，采用位运算方式，减少字段，贴近系统的Gravity，降低使用成本

```kotlin
textExplanation(R.id.imageView) {
    text = "图片xx"
    textStyle = R.style.MyTextStyle
    guideGravity = GuideGravity.CENTER_HORIZONTAL or GuideGravity.BOTTOM
}
```