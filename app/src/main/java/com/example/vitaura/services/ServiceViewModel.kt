package com.example.vitaura.services

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vitaura.MainRepository
import com.example.vitaura.helpers.ServerHelper

class ServiceViewModel : ViewModel() {

    val servicesMap: MutableMap<String, Service> = mutableMapOf()
    var serviceTitle: String = ""
    var serviceTypeTitle: String = ""
    var services: MutableLiveData<MutableList<Service?>> = MutableLiveData(mutableListOf())

    var mainRepository = MainRepository
    var serviceId = ""
}