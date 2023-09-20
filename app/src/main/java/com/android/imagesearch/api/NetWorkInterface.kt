package com.android.imagesearch.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("image")
    suspend fun getImage(@Header("Authorization") authKey: String,
                         @QueryMap param:HashMap<String,String>) : Kakao
}