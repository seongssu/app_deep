package com.android.appdevelop_deep.Miseya

import retrofit2.http.GET
import retrofit2.http.QueryMap

//해당 http에서 데이터를 요청하고 받아온다.
interface NetWorkInterface {
    @GET("getMinuDustFrcstDspth")   //요청 주소(서비스URL아님)
    suspend fun getDust(@QueryMap param:HashMap<String, String>): Dust
    //suspend : 코루틴 비동기작업
    //@QueryMap : Restfit 라이브러리, http로 데이터를 요청하고 받아오는데 편리하다.
}