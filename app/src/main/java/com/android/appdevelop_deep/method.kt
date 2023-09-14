package com.android.appdevelop_deep

import android.content.Context
import android.widget.Toast

fun Context.shortToast(message: String, time: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,time).show()
}
fun Context.longToast(message: String, time: Int = Toast.LENGTH_LONG){
    Toast.makeText(this,message,time).show()
}