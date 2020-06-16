package com.example.im.presenter

import com.example.im.contract.SplashContract
import com.hyphenate.chat.EMClient

class SplashPresenter(val view:SplashContract.View) : SplashContract.Presenter {


    override fun checkLoginStatus() {
        if (isLoggedIn()) view.onLoggedIn() else view.onNotLoggedIn()

    }

    private fun isLoggedIn(): Boolean {
        return EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore
    }


}