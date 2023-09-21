package com.android.imagesearch.api

import android.provider.MediaStore.Video
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface NetWorkInterface {
    @GET("image")
    suspend fun getImage(
        @Header("Authorization") authKey: String,
        @QueryMap param: HashMap<String, String>
    ): Kakao

    @GET("vclip")
    suspend fun getVideo(
        @Header("Authorization") authKey: String,
        @QueryMap param: HashMap<String, String>
    ) : VideoData
}
