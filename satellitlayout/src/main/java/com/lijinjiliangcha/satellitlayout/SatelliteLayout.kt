package com.lijinjiliangcha.satellitlayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.view.children
import org.json.JSONObject

class SatelliteLayout : ViewGroup {

    //view动画设定
    private val optionMap: HashMap<View, Option?> by lazy { HashMap() }

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
                optionMap.values.forEach {
                    it?.let {
                        if (it.speed != 0) {
                            val dis = it.speed * value
                            //处理顺时针逆时针
                            if (it.revolutionDirection == Direction.CLOCKWISE)
                                it.angle += dis
                            else
                                it.angle -= dis
                        }
                    }
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
                val childL = (cos(option.angle) * option.r + cx - childWidth / 2).toInt()
                val childT = (sin(option.angle) * option.r + cy - childHeight / 2).toInt()
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
    fun getOption(view: View): Option? {
        return optionMap[view]
    }

    //获取指定index的view的option
    fun getOption(index: Int): Option? {
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

    //sin函数
    private fun sin(angle: Float): Float {
        return Math.sin(Math.toRadians(angle.toDouble())).toFloat()
    }

    //cos函数
    private fun cos(angle: Float): Float {
        return Math.cos(Math.toRadians(angle.toDouble())).toFloat()
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