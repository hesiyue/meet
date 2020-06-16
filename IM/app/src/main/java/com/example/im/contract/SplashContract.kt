package com.example.im.contract

interface SplashContract {

    interface Presenter : BasePresenter {
        fun checkLoginStatus()
    }

    interface View {
        fun onNotLoggedIn() // 没有登陆的UI处理
        fun  onLoggedIn() //已经登录的UI的处理
    }
}