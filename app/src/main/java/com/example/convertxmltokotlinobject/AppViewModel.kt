package com.example.convertxmltokotlinobject

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.convertxmltokotlinobject.repository.AppRepository
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {
    private val repository = AppRepository()

    private var _xml = mutableStateOf("")
    // read-only state
    val xml = _xml

    fun getXML() {
        viewModelScope.launch {
            _xml.value = repository.getXML()
        }
    }
}