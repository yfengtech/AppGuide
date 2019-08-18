# AppGuide 开发摘要

## 背景
2019/7/11

收集需求，思考设计思路

大致需要满足一下几点：

1. 方便使用，无入侵式
2. 有序性，可以依次展示所需的引导页
3. 对指定控件的高亮显示
4. 层级较高，如果有可能的话，盖住类似popupWindow的控件
5. 考虑跨进程

## 调研
2019/7/11

### 1. 无入侵性

1. 思考1
    * 如果通过具体activity中的根布局`window.decorView.findViewById<ViewGroup>(android.R.id.content)`做手脚，直接添加的话很可能无法盖住全部内容(例如带有actionBar主题的activity)
    * 直接获取DecorView增加布局，或者从activity中的布局入手，无法盖住popupWindow之类的东西
2. 思考2
    * 考虑用window来实现，`activity.windowManager.addView`，为`window`指定`type`，经测试，能够保证盖住内容区和popupWindow等弹出的控件。
    * type类型是`TYPE_APPLICATION`可以盖住`popupwindow`，大于2000可以盖住dialog（但需要系统权限）；因为仅仅是引导页，这里考虑用应用级的window

### 2. 有序性

思路：在新的window中创建一个父容器FrameLayout，后续需要加的引导页，放在父布局内部，通过父布局维护子布局层级关系

```kotlin
    /**
     * 展示一个guide view，加入到跟布局上，并显示
     */
    fun showGuideView(view: View) {
        rootView.addView(view)
        update()
    }

    /**
     * 移除顶部guide
     *
     * @return  返回剩余的guide view数量
     */
    fun removeTopGuideView(): Int {
        if (rootView.childCount > 0) {
 rootView.removeViewAt(rootView.childCount - 1)
        }
        update()
        return rootView.childCount
    }

    /**
     * 遍历所有guide view，只显示最后加入的引导
     */
    private fun update() {
        if (rootView.childCount > 0) {
            for (i in 0 until rootView.childCount) {
                // 只显示最后的一个
                if (i == rootView.childCount - 1) {
 rootView.getChildAt(i).visibility = View.VISIBLE
                } else {
 rootView.getChildAt(i).visibility = View.GONE
                }
            }
        }
    }
```

### 3. 高亮控件
思路：通过指定方法传入指定控件view和引导文案（样式等），获取该控件的位置，创建一个蒙层盖在上层，高亮指定位置

```kotlin
val rect = Rect()
// 获取的是相对于屏幕的位置
highLightingView.getGlobalVisibleRect(rect)
...
```

> 计算位置时需要考虑在非全屏和全屏下状态栏的情况

### 其他需要注意的
> 注意内存泄漏，使用activity的application，监听生命周期，销毁时移除引导windows内的所有内容