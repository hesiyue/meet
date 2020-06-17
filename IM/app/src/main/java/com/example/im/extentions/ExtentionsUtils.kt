package com.example.im.extentions

fun String.isValidUserName() : Boolean = this.matches(Regex("^[a-zA-Z]\\w{2,19}$"))


fun String.isValidPassword() : Boolean = this.matches(Regex("^[0-9]{3,20}$"))