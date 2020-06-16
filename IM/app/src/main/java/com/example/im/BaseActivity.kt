package com.example.im

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        init()
    }

    open fun init() {
        //初始化一些公共的功能，子类也可以复写该方法，完成自己的初始化
    }

    //子类要实现该方法并返回一个布局资源的id
    abstract fun getLayoutResId(): Int
}