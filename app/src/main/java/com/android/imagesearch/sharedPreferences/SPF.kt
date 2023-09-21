package com.android.imagesearch.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class SPF(context:Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("spf",Context.MODE_PRIVATE)

    fun saveTitle(title:String){
        val editor = sharedPreferences.edit()
        editor.putString("title",title)
        editor.apply()
    }
    fun getTitle() : String {
        return sharedPreferences.getString("title","")?:""
    }


    fun saveData(items:String){
        val editor = sharedPreferences.edit()
        editor.putString("items",items)
        editor.apply()
    }

    fun getData() : String {
        return sharedPreferences.getString("items","") ?: ""
    }

}