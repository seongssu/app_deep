package com.android.imagesearch.sharedPreferences

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoData(
    val title : String,
    val datetime : String,
    val thumbnail : String,
    var isLike : Boolean = false
) : Parcelable
