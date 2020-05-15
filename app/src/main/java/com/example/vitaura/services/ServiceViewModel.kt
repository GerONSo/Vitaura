package com.example.vitaura.services

import androidx.lifecycle.ViewModel
import com.example.vitaura.ServerHelper

class ServiceViewModel : ViewModel() {

    val servicesMap: MutableMap<String, Services> = mutableMapOf()

    fun getOrLoadService(id: String, cacheToViewModel: (services: Services) -> Unit): Services? =
        servicesMap.getOrElse(id) {
            ServerHelper.getService(id, cacheToViewModel)
            null
        }
}