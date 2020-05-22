package com.example.vitaura.doctors

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DoctorViewModel : ViewModel() {
    val position: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }
}