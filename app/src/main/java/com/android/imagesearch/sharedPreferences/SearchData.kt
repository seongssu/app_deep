package com.android.imagesearch.sharedPreferences

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchData(
    val type:Int,
    val display_sitename : String,
    val datetime : String,
    val image_url : String,
    var isLike : Boolean = false
) : Parcelable
