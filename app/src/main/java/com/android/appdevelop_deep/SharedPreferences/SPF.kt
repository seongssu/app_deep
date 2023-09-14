package com.android.appdevelop_deep.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

class SPF(context:Context) {
    private val sharedPreferences : SharedPreferences = context.getSharedPreferences("pref",
        Context.MODE_PRIVATE)

    fun saveData(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.apply()
    }

    fun loadData(): String {
        return sharedPreferences.getString("name", "") ?: ""
    }
}