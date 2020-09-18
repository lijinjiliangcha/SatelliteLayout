package com.lijinjiliangcha.satellitlayout.option

import com.lijinjiliangcha.satellitlayout.Direction
import com.lijinjiliangcha.satellitlayout.contract.BaseOption

class Option : BaseOption {

    //半径(view中心距圆心距离)
    var r = 0
        set(value) {
            field = if (value < 0) 0 else value
        }

    //当前位置角度
    var angle = 0f

    //公转角速度(每秒位移角度)
    var speed = 0

    //公转方向
    var revolutionDirection: Direction = Direction.CLOCKWISE

    constructor() {
        this.r = 0
        this.angle = 0f
        this.speed = 0
        this.revolutionDirection = Direction.CLOCKWISE
    }

    constructor(r: Int, angle: Float, speed: Int, revolutionDirection: Direction) {
        this.r = r
        this.angle = angle
        this.speed = speed
        this.revolutionDirection = revolutionDirection
    }

    override fun onAnimCallback(delta: Float) {
        if (speed != 0) {
            val dis = speed * delta
            //处理顺时针逆时针
            if (revolutionDirection == Direction.CLOCKWISE)
                angle += dis
            else
                angle -= dis
        }
    }

    override fun getLeft(width: Int, cx: Int): Int {
        return (cos(angle.toDouble()) * r + cx - width / 2).toInt()
    }

    override fun getTop(height: Int, cy: Int): Int {
        return (sin(angle.toDouble()) * r + cy - height / 2).toInt()
    }

}