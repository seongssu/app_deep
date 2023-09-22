package com.android.imagesearch.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

//sharedpreferences 클래스
class SPF(context:Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("spf",Context.MODE_PRIVATE)

    fun saveTitle(title:String){                                    //마지막검색기록을 저장하는 함수
        val editor = sharedPreferences.edit()
        editor.putString("title",title)
        editor.apply()
    }
    fun getTitle() : String {                                        //마지막검색기록을 불러오는 함수
        return sharedPreferences.getString("title","")?:""
    }


    fun saveData(items:String){                                     //서버에서 받아온데이터를 저장하는 함수
        val editor = sharedPreferences.edit()
        editor.putString("items",items)
        editor.apply()
    }

    fun getData() : String {                                    //저장된 데이터를 불러오는 함수
        return sharedPreferences.getString("items","") ?: ""
    }

}