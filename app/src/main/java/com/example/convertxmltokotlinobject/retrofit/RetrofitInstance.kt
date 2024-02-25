package com.example.convertxmltokotlinobject.retrofit

import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://ndlsearch.ndl.go.jp/api/"

    private val retrofit: Retrofit by lazy {
        // No Use ConverterFactory. So it returns XML as String.
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
    }

    val api: GetXMLService by lazy {
        retrofit.create(GetXMLService::class.java)
    }
}