package com.example.convertxmltokotlinobject

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertxmltokotlinobject.repository.AppRepository
import com.example.convertxmltokotlinobject.retrofit.Books
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {
    private val repository = AppRepository()

    private var _xml = mutableStateOf(Books(emptyList()))
    // read-only state
    val xml = _xml

    fun getXML() {
        viewModelScope.launch {
            _xml.value = repository.getXML()
        }
    }
}