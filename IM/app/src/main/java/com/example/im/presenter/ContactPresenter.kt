package com.example.im.presenter

import com.example.im.contract.ContactContract
import com.example.im.data.ContactListItem
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync

class ContactPresenter(val view:ContactContract.View) : ContactContract.Presenter {


    val contactListItems = mutableListOf<ContactListItem>()


    override fun loadContacts() {
        doAsync {
            //再次加载数据，先清空集合
            contactListItems.clear()
            try {
                val usernames = EMClient.getInstance().contactManager().allContactsFromServer
                usernames.sortBy {
                    it[0]
                }

                usernames.forEachIndexed { index, s ->
                    //判断是否显示首字符
                    val showFirstLetter = index == 0 || s[0] != usernames[index-1][0]
                    val contactListItem = ContactListItem(s,s[0].toUpperCase(),showFirstLetter)
                    contactListItems.add(contactListItem)
                }

                uiThread { view.loadContactsSuccess() }
            }catch (e: HyphenateException) {
                uiThread { view.loadContactsFailed() }
            }

        }

    }
}