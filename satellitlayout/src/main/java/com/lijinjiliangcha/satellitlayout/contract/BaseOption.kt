package com.lijinjiliangcha.satellitlayout.contract

abstract class BaseOption {

    /**
     * 动画执行时回调
     * @param delta 两次回调间的差值
     */
    abstract fun onAnimCallback(delta: Float)

    /**
     * 获取option对应的view的左边坐标
     * @param width option对应的view的宽度
     * @param cx SatelliteLayout中心点的x坐标
     */
    abstract fun getLeft(width: Int, cx: Int): Int

    /**
     * 获取option对应的view的上边坐标
     * @param height option对应的view的高度
     * @param cy SatelliteLayout中心点的y坐标
     */
    abstract fun getTop(height: Int, cy: Int): Int

    //sin函数
    fun sin(angle: Double): Double {
        return Math.sin(Math.toRadians(angle))
    }

    //cos函数
    fun cos(angle: Double): Double {
        return Math.cos(Math.toRadians(angle))
    }
}