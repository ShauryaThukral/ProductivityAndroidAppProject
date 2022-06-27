package com.example.productivityapp
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BoredApi {
    @GET("activity/")
    fun getResponse(): Call<Result>
}

object RetrofitHelper {
    val baseUrl = "http://www.boredapi.com/api/"
    fun getInstance(): Retrofit{
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}