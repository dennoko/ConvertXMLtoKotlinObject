package com.example.convertxmltokotlinobject.repository

import com.example.convertxmltokotlinobject.retrofit.RetrofitInstance

class AppRepository {
    private val apiService = RetrofitInstance.api

    suspend fun getXML() = apiService.getXML()
}