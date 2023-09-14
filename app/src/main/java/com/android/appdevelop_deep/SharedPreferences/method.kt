package com.android.appdevelop_deep.SharedPreferences

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, time: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,time).show()
}