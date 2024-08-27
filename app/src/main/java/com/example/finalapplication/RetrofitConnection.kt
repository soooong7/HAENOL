package com.example.finalapplication

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection{

    companion object {

        private const val BASE_URL_Php = "http://172.30.1.26/"

        var phpNetworkService : NetworkService
        val phpRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL_Php)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        private const val BASE_URL1 = "https://busan-7beach.openapi.redtable.global/"

        val JsonNetworkService : NetworkService
        val JsonRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        private const val BASE_URL2 = "http://apis.data.go.kr/1192000/service/"

        var xmlNetworkService : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        init{
            phpNetworkService = phpRetrofit.create(NetworkService::class.java)
            JsonNetworkService = JsonRetrofit.create(NetworkService::class.java)
            xmlNetworkService = xmlRetrofit.create(NetworkService::class.java)
        }

    }
}