package com.example.im

import android.os.Handler
import com.example.im.contract.SplashContract
import com.example.im.presenter.SplashPresenter
import org.jetbrains.anko.startActivity


class SplashActivity : BaseActivity(),SplashContract.View {

    val presenter = SplashPresenter(this)


    companion object {
        var DELAY : Long = 2000
    }


    val handler by lazy {
        Handler()
    }

    init {
        super.init()
        presenter.checkLoginStatus()
    }


    override fun getLayoutResId(): Int = R.layout.activity_splash


    //延时2s跳转到登陆页面
    override fun onNotLoggedIn() {
        handler.postDelayed({
            startActivity<LoginActivity>()
            finish()
        }, DELAY)

    }

    //跳转到主界面
    override fun onLoggedIn() {
        startActivity<MainActivity>()
        finish()
    }
}