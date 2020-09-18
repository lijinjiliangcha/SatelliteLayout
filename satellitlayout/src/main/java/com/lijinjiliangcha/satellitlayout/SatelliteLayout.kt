package com.lijinjiliangcha.satellitlayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.lijinjiliangcha.satellitlayout.contract.BaseOption

class SatelliteLayout : ViewGroup {

    //view动画设定
    private val optionMap: HashMap<View, BaseOption?> by lazy { HashMap() }

    //动画
    private val anim: ValueAnimator by lazy {
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            //使用自定义插值器
            interpolator = SatelliteInterpolator()
            addUpdateListener {
                if (optionMap.size == 0)
                    return@addUpdateListener
                val value = it.animatedValue as Float
                optionMap.values.forEach { option ->
                    option?.onAnimCallback(value)
                }
                requestLayout()
            }
            addListener(object : SatelliteAnimatorListener() {
                override fun onEnd(animation: Animator?) {
                    animation?.start()
                }
            })
        }
    }

    //控件中心位置
    private var cx = 0
    private var cy = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        children.forEach {
            val childWidth = it.measuredWidth
            val childHeight = it.measuredHeight
            val option = optionMap[it]
            if (option != null) {
                //计算卫星子View所在位置
                val childL = option.getLeft(childWidth, cx)
                val childT = option.getTop(childHeight, cy)
                it.layout(childL, childT, childL + childWidth, childT + childHeight)
            } else {
                //非卫星子View放在中间
                val childL = cx - childWidth / 2
                val childT = cy - childHeight / 2
                it.layout(childL, childT, childL + childWidth, childT + childHeight)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cx = w / 2
        cy = h / 2
    }

    //获取指定view的option
    fun getOption(view: View): BaseOption? {
        return optionMap[view]
    }

    //获取指定index的view的option
    fun getOption(index: Int): BaseOption? {
        if (index > childCount)
            return null
        return getOption(getChildAt(index))
    }

    //添加一个
    fun addEntry(entry: Entry) {
        addView(entry.view)
        optionMap[entry.view] = entry.option
    }

    //添加多个
    fun addEntry(list: List<Entry>) {
        list.forEach {
            addEntry(it)
        }
    }

    //开始动画
    fun start() {
        if (!anim.isRunning)
            anim.start()
    }

    //停止动画
    fun stop() {
        anim.cancel()
    }

    override fun removeAllViews() {
        super.removeAllViews()
        optionMap.clear()
    }

    override fun removeView(view: View?) {
        super.removeView(view)
        optionMap.remove(view)
    }

    override fun removeViewAt(index: Int) {
        val view = getChildAt(index)
        super.removeViewAt(index)
        optionMap.remove(view)
    }

}