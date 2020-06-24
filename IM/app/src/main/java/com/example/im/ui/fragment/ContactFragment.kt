package com.example.im.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.im.R
import com.example.im.adapter.ContactListAdapter
import com.example.im.adapter.EMContactListenerAdapter
import com.example.im.contract.ContactContract
import com.example.im.presenter.ContactPresenter
import com.example.im.ui.activity.AddFriendActivity
import com.example.im.widget.SlideBar
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.header.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.startActivity

class ContactFragment : BaseFragment(),ContactContract.View {
    override fun getLayoutResId() = R.layout.fragment_contacts

    val presenter = ContactPresenter(this)


    override fun init() {
        super.init()
        headerTitle.text = getString(R.string.contact)
        add.visibility = View.VISIBLE
        add.setOnClickListener{context?.startActivity<AddFriendActivity>()}


        swipeRefreshLayout.apply {
            setColorSchemeResources(R.color.qq_blue)
            isRefreshing = true
            setOnRefreshListener {
                presenter.loadContacts()
            }
        }


        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ContactListAdapter(context,presenter.contactListItems)
        }

        EMClient.getInstance().contactManager().setContactListener(object :EMContactListenerAdapter() {

            override fun onContactDeleted(p0: String?) {
                //重新获取联系人的数据
                presenter.loadContacts()
            }

        })

        slideBar.onSectionChangeListener = object :SlideBar.OnSectionChangeListener {


            override fun onSectionChange(firstLetter: String) {
                section.visibility = View.VISIBLE
                section.text = firstLetter
                recyclerView.smoothScrollToPosition(getPosition(firstLetter))

            }

            override fun onSlideFinish() {
                section.visibility = View.GONE

            }

        }

        presenter.loadContacts()
    }

    private fun getPosition(firstLetter:String) : Int =
        presenter.contactListItems.binarySearch {
            contactListItem ->  contactListItem.firstLetter.minus(firstLetter[0])
        }


    override fun loadContactsSuccess() {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.adapter?.notifyDataSetChanged()

    }

    override fun loadContactsFailed() {
        swipeRefreshLayout.isRefreshing = false
        context?.toast(R.string.load_contacts_failed)

    }
}