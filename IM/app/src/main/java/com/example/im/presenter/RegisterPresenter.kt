package com.example.im.presenter

import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.im.contract.RegisterContract
import com.example.im.extentions.isValidPassword
import com.example.im.extentions.isValidUserName
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync

class RegisterPresenter(val view : RegisterContract.View) : RegisterContract.Presenter {


    override fun register(userName: String, password: String, confirmPassword: String) {
        if(userName.isValidUserName()) {
            if(password.isValidPassword()) {
                if(password.equals(confirmPassword)) {
                    view.onStartRegister()
                    //开始真正的注册逻辑
                    registerBmob(userName,password)

                }else view.onConfirmPasswordError()
            }else view.onPasswordError()
        }else view.onUserNameError()
    }

    private fun registerBmob(userName: String, password: String) {
        val bu = BmobUser()

        bu.username = userName
        bu.setPassword(password)

        bu.signUp<BmobUser>(object : SaveListener<BmobUser>() {
            override fun done(s: BmobUser?, e: BmobException?) {
                if(e == null) {
                    //注册到环信
                    registerEaseMob(userName,password)
                }else {
                    if (e.errorCode == 202) view.onUserExist()
                    else view.onRegisterFaild()
                }
            }
        })

    }

    private fun registerEaseMob(userName: String, password: String) {
        doAsync {
            try {
                //注册失败会抛出HyphenateException
                EMClient.getInstance().createAccount(userName,password)
                uiThread { view.onRegisterSuccess() }
            }catch (e : HyphenateException) {
                //注册失败
                uiThread { view.onRegisterFaild() }
            }

        }

    }
}