package com.example.im.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.im.R
import com.example.im.data.ContactListItem
import com.example.im.ui.activity.ChatActivity
import com.example.im.widget.ContactListItemView
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ContactListAdapter(
    val context: Context,
    val contactListItems: MutableList<ContactListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactListItemViewHolder(ContactListItemView(context))

    }

    override fun getItemCount() = contactListItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val contactListItemView =  holder.itemView as ContactListItemView
        contactListItemView.bindView(contactListItems[position])
        val username = contactListItems[position].userName
        contactListItemView.setOnClickListener { context.startActivity<ChatActivity>("username" to username) }
        contactListItemView.setOnLongClickListener {
            val message = String.format(context.getString(R.string.delete_friend_message),username)
            AlertDialog.Builder(context)
                .setTitle(R.string.delete_friend_title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel,null)
                .setPositiveButton(R.string.confirm, { dialogInterface, i ->
                    deleteFriend(username)
                })
                .show()
            true
        }

    }

    private fun deleteFriend(username: String) {
        EMClient.getInstance().contactManager().aysncDeleteContact(
            username,object : EMCallBackAdapter() {

                override fun onSuccess() {
                    context.runOnUiThread { toast(R.string.delete_friend_success) }

                }

                override fun onError(p0: Int, p1: String?) {
                    context.runOnUiThread { toast(R.string.delete_friend_failed) }
                }

            }
        )

    }


    class ContactListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}