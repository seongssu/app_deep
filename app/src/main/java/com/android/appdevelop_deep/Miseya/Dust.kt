package com.android.appdevelop_deep.Miseya

import com.google.gson.annotations.SerializedName

data class Dust(val response: DustResPonse)

data class DustResPonse(
    @SerializedName("body")
    val dustBody: DustBody,
    @SerializedName("header")
    val dustHeader : DustHead
)

data class DustBody(
    val totalCount:Int,
    @SerializedName("items")
    val dustItem:MutableList<DustItem>?,
    val pageNo:Int,
    val numOfRows:Int
)

data class DustHead(
    val resultMsg : String,
    val resultCode : String
)

data class DustItem(
    val so2Grade : String,
    val coFlag : String?,
    val KhaiValue : String,
    val so2Value : String,
    val coValue : String,
    val pm25Flag : String?,
    val pm10Flag : String?,
    val o3Grade : String?,
    val pm10Value: String,
    val khaiGrade : String,
    val pm25Value : String,
    val sidoName : String,
    val no2Flag : String?,
    val no2Grade : String,
    val o3Flag : String?,
    val pm25Grade : String,
    val so2Flag : String?,
    val dataTime : String,
    val coGrade : String,
    val no2Value : String,
    val stationName : String,
    val pm10Grade : String,
    val o3Value : String
)
