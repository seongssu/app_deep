package com.android.appdevelop_deep.Miseya


import com.android.appdevelop_deep.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//인터페이스에서 받아온 데이터를 관리한다.
object NetWorkClient {
    private val DUST_BASE_URL = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc"

    private fun createOkHttpClient(): OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }
    private  val dustRestrofit = Retrofit.Builder()
        .baseUrl(DUST_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(
            createOkHttpClient()).build()
        val dustNetWork : NetWorkInterface = dustRestrofit.create(NetWorkInterface::class.java)

}