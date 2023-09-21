package com.android.imagesearch.Method

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat

fun Context.shortToast(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, time).show()
}

fun timesort(time : String) : String {
    try{
        val input_time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+09:00")
        val date = input_time.parse(time)

        val output_time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return output_time.format(date)
    }
    catch (e: Exception) {
        e.printStackTrace()
        return "Invalid input time"
    }
}