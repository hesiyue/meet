package com.example.im.ui.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {


    val progressDialog by lazy {
        ProgressDialog(this)
    }

    val inputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    fun hideSoftKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken,0)
    }

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


    fun showProgress(message:String) {
        progressDialog.setMessage(message)
        progressDialog.show()
    }

    fun dismissProgress() {
        progressDialog.dismiss()
    }
}