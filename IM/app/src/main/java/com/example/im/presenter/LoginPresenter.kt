package com.example.im.presenter

import com.example.im.adapter.EMCallBackAdapter
import com.example.im.contract.LoginContract
import com.example.im.extentions.isValidPassword
import com.example.im.extentions.isValidUserName
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient

class LoginPresenter(val  view:LoginContract.View) : LoginContract.Presenter {


    override fun login(userName: String, password: String) {
        if (userName.isValidUserName()) {
            //用户名合法，继续校验密码
            if (password.isValidPassword()) {
                //密码合法，开始校验
                view.onStartLogin()
                loginEaseMob(userName,password)
            }else view.onPasswordError()
        }else view.onUserNameError()


    }

    private fun loginEaseMob(userName: String, password: String) {
        EMClient.getInstance().login(userName,password,object : EMCallBackAdapter() {

            //在子线程回调
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                //在UI线程通知View层
                uiThread { view.onLoggedInSuccess() }

            }

            override fun onError(p0: Int, p1: String?) {
                uiThread { view.onLoggedInFailed() }

            }
        })

    }

}