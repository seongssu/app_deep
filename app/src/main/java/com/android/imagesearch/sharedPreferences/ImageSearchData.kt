package com.android.imagesearch.sharedPreferences

import android.net.Uri
import android.os.Parcelable
import com.android.imagesearch.api.common
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageSearchData(
    val display_sitename : String,
    val datetime : String,
    val image_url : String,
    var isLike : Boolean = false
) : Parcelable, common
