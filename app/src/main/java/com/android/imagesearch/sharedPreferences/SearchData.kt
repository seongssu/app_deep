package com.android.imagesearch.sharedPreferences

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchData(              //API를 이용해서 받아온데이터를 다시 담는 리스트의 데이터 클래스
    val type:Int,
    val display_sitename : String,
    val datetime : String,
    val image_url : String,
    var isLike : Boolean = false
) : Parcelable
