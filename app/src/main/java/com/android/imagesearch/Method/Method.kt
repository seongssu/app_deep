package com.android.imagesearch.Method

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat

fun Context.shortToast(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, time).show()
}

fun timesort(time : String) : String {
    try{
        val input_time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+09:00")    //받아온 데이터 형식 포맷 정해줌
        val date = input_time.parse(time)                                       //받아온 데이터를 파싱

        val output_time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")               //변환할 데이터 형식 포멧 지정
        return output_time.format(date)                                                 //그곳에 받아온 데이터를 넣어줘서 포맷
     }
    catch (e: Exception) {                  //넘겨줄때 데이터형식이 다른경우도 있으므로 예외처리
        e.printStackTrace()             // 예외가 생긴곳을 알려준다. 예외의 원인과 위치를 찾을 수 있다.
        return "error"
    }
}