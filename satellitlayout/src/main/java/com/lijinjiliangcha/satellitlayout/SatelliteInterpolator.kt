package com.lijinjiliangcha.satellitlayout

import android.util.Log
import android.view.animation.Interpolator

//卫星插值器
class SatelliteInterpolator : Interpolator {

    //上一次的值
    private var oldInput = 0f

    override fun getInterpolation(input: Float): Float {
        if (oldInput > input)
            oldInput = 0f
        //使返回值为两次差值之间的差值，保证角速度的准确性
        val delta = input - oldInput
        oldInput = if (input == 1f) 0f else input
        return delta
    }
}