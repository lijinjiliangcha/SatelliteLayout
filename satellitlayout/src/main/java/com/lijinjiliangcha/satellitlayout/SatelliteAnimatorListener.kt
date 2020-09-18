package com.lijinjiliangcha.satellitlayout

import android.animation.Animator

abstract class SatelliteAnimatorListener : Animator.AnimatorListener {

    private var isCancel = false

    override fun onAnimationStart(animation: Animator?) {
    }

    override fun onAnimationEnd(animation: Animator?) {
        if (isCancel) {
            isCancel = false
            return
        }
        onEnd(animation)
    }

    override fun onAnimationCancel(animation: Animator?) {
        isCancel = true
    }

    override fun onAnimationRepeat(animation: Animator?) {
    }

    abstract fun onEnd(animation: Animator?)
}