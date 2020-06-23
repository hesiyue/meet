package com.example.im.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.im.R
import org.jetbrains.anko.sp

class SlideBar(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    var sectionHeight = 0f

    var paint = Paint()

    var textBaseline = 0f

    var onSectionChangeListener: OnSectionChangeListener? = null


    companion object {
        private val SECTIONS = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L","M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //计算每个字符分配的高度
        sectionHeight = (h / SECTIONS.size).toFloat()

        val fontMetrics = paint.fontMetrics

        //计算绘制文本的高度
        val textHeihgt = fontMetrics.descent - fontMetrics.ascent

        //计算基准线
         textBaseline = sectionHeight / 2 + (textHeihgt/2 - fontMetrics.descent)
    }

    init {
        paint.apply {
            color = resources.getColor(R.color.qq_section_text_color)
            textSize = sp(12).toFloat()

            //对齐居中
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas?) {
        //绘制所有的字母
        val x = width * 1.0f/2
        var baseline = textBaseline
        SECTIONS.forEach {
            canvas?.drawText(it,x,baseline,paint)
            //更新y，绘制下一个字母
            baseline += sectionHeight
        }
    }

    interface OnSectionChangeListener{
        fun onSectionChange(firstLetter: String)
        fun onSlideFinish()//滑动结束的回调
    }
}