package com.android.imagesearch.api

import com.android.imagesearch.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetWorkClient {
    private val KakaoUrl = "https://dapi.kakao.com/v2/search/"

    private fun createOkHttpClient(): OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val Restrofit = Retrofit.Builder()
        .baseUrl(KakaoUrl).addConverterFactory(GsonConverterFactory.create()).client(
            createOkHttpClient()
        ).build()
    val KakaoNetWork : NetWorkInterface = Restrofit.create(NetWorkInterface::class.java)
}