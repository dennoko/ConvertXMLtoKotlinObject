package com.example.convertxmltokotlinobject.retrofit

import retrofit2.http.GET

interface GetXMLService {
    @GET("opensearch?title=androidアプリ開発")
    suspend fun getXML(): String
}