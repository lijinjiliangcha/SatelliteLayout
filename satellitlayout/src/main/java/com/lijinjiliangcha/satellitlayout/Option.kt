package com.lijinjiliangcha.satellitlayout

import androidx.annotation.IntDef

class Option {

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

}