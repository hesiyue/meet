package com.example.im.app

import android.app.Application
import cn.bmob.v3.Bmob
import com.example.im.BuildConfig
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions

class IMApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //初始化
        EMClient.getInstance().init(applicationContext, EMOptions())
        //在做打包混淆时，关闭debug模式，避免消耗不必需要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG)
        Bmob.initialize(applicationContext,"f2fcc6c704c105f8ef9f6b880f05afbf")
    }
}