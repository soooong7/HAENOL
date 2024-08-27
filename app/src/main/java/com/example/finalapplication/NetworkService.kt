package com.example.finalapplication

import com.example.joyceapplication.JsonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkService {
    @GET("PHP_connection.php")
    fun getPhpList(
        @Query("name") name:String
    ) : Call<PhpResponse>

    @GET("api/food/img")
    fun getJsonList(
        @Query("page_No") pageNo: Int,
        @Query("serviceKey") serviceKey:String
    ) : Call<JsonResponse>

    @GET("OceansBeachInfoService1/getOceansBeachInfo1")
    fun getXmlList(
        @Query("SIDO_NM") name:String,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("resultType") resultType:String,
        @Query("ServiceKey") apiKey:String
    ) : Call<XmlResponse>


}