package com.yfengtech.guide.function

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import com.yfengtech.guide.AppGuide
import com.yfengtech.guide.OnGuideChangeListener
import com.yfengtech.guide.R
import kotlinx.android.synthetic.main.fragment_lifecycle.*
import org.jetbrains.anko.textAppearance

/**
 * DEMO
 * 演示 监听引导页显示隐藏的回调
 * 演示 引导过程中 中断和继续的功能
 *
 * @created yfengtech
 * @date 2019-07-17 10:50
 */
class LifeCycleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lifecycle, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AppGuide.with(this)
            .setOnGuideChangeListener(object : OnGuideChangeListener {
                override fun onGuideShow(action: String) {
                    val str = textView.text.toString() + "$action 显示 \n"
                    textView.text = str
                }

                override fun onGuideDismiss(action: String) {
                    val str = textView.text.toString() + "$action 隐藏 \n"
                    textView.text = str
                }
            })
    }

    override fun onDetach() {
        super.onDetach()
        AppGuide.with(this).setOnGuideChangeListener(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn1.setOnClickListener {
            AppGuide.with(this).startGuide {
                // 显示
                for (i in 1..12) {
                    scene {
                        //                        bgColor = Color.parseColor("#40ff0000")
                        action = "引导页$i"
                        viewElement {
                            this.view = Button(context).apply {
                                text = "引导页$i"
                                textAppearance = R.style.MyTextStyle
                            }
                            lparams = FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.WRAP_CONTENT,
                                FrameLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                gravity = Gravity.CENTER
                            }
                        }
                        onGuideSceneClick {
                            // 每连续显示4次引导，暂停一次
                            if (i % 4 == 0) {
                                AppGuide.with(context!!).dismissCurrent()
                            } else {
                                AppGuide.with(context!!).showNext()
                            }
                        }
                    }
                }
            }

            btn2.setOnClickListener {
                if (!AppGuide.with(context!!).showNext()) {
                    Toast.makeText(context, "没有可显示的了", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}