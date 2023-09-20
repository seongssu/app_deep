package com.android.imagesearch.Method

import android.content.Context
import android.widget.Toast

fun Context.shortToast(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, time).show()
}