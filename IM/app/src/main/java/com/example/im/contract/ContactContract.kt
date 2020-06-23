package com.example.im.contract

interface ContactContract {

    interface Presenter : BasePresenter {
        fun loadContacts()
    }


    interface View {
        fun loadContactsSuccess()
        fun loadContactsFailed()
    }
}