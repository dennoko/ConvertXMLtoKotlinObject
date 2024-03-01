package com.example.convertxmltokotlinobject.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://ndlsearch.ndl.go.jp/api/"

    private val retrofit: Retrofit by lazy {
        // No Use ConverterFactory. So it returns XML as String.
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(CustomConverterFactory())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    val api: GetXMLService by lazy {
        retrofit.create(GetXMLService::class.java)
    }
}