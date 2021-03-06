package com.example.im.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.im.R
import com.example.im.factory.FragmentFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun getLayoutResId() = R.layout.activity_main

    override fun init() {
        super.init()
        bottomBar.setOnTabSelectListener  { tabId ->
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.replace(R.id.fragment_frame,
                FragmentFactory.instance.getFragment(tabId)!!
            )
            beginTransaction.commit()
        }
    }
}
