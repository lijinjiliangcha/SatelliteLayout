package com.lijinjiliangcha.satellitelayout_master

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.lijinjiliangcha.roundedimageview.RoundedImageView
import com.lijinjiliangcha.satellitlayout.Direction
import com.lijinjiliangcha.satellitlayout.Entry
import com.lijinjiliangcha.satellitlayout.option.Option
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val iv_blue = getImageView().apply { setBackgroundColor(Color.BLUE) }
        val iv_red = getImageView().apply { setBackgroundColor(Color.RED) }
        val iv_green = getImageView().apply { setBackgroundColor(Color.GREEN) }
        val iv_black = getImageView().apply { setBackgroundColor(Color.BLACK) }
        val iv_accent = getImageView().apply { setBackgroundResource(R.color.colorAccent) }

        val list = ArrayList<Entry>()
        list.add(Entry(iv_blue, Option(500, 0f, 10, Direction.CLOCKWISE)))
        list.add(Entry(iv_red, Option(800, 90f, 20, Direction.ANTICLOCKWISE)))
        list.add(Entry(iv_green, Option(300, 180f, 30, Direction.CLOCKWISE)))
        list.add(Entry(iv_black, Option(100, 270f, 40, Direction.ANTICLOCKWISE)))
        list.add(Entry(iv_accent, null))

        sl.addEntry(list)

        //开始
        btn_start.setOnClickListener {
            sl.start()
        }
        //停止
        btn_end.setOnClickListener {
            sl.stop()
        }
        //测试半径增加
        btn_black.setOnClickListener {
            val option = sl.getOption(iv_black)
            option?.let {
                (it as Option).r += 10
            }
        }
        //点击事件
        iv_blue.setOnClickListener {
            Toast.makeText(this, "小蓝", Toast.LENGTH_SHORT).show()
        }
        iv_red.setOnClickListener {
            Toast.makeText(this, "小红", Toast.LENGTH_SHORT).show()
        }
        iv_green.setOnClickListener {
            Toast.makeText(this, "小绿", Toast.LENGTH_SHORT).show()
        }
        iv_black.setOnClickListener {
            Toast.makeText(this, "小黑", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getImageView(): RoundedImageView {
        val view = LayoutInflater.from(this).inflate(R.layout.item_satellite, sl, false)
        return view.findViewById(R.id.iv_satellite)
    }

    override fun onStart() {
        super.onStart()
        sl.start()
    }

    override fun onStop() {
        super.onStop()
        sl.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        sl.stop()
    }
}
