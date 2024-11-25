package com.example.slambook

import android.provider.ContactsContract.CommonDataKinds.Nickname

data class Users(

    val name: String = "",
    val nickname: String =  "",
    val hobbies: String = "",
    val age: Int = 0,
    val gender : String = ""


)
