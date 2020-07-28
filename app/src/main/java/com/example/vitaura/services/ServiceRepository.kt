package com.example.vitaura.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.example.vitaura.R

object ServiceRepository {
    var serviceTypes: List<String> = listOf(
        "Лицо",
        "Тело",
        "Волосы",
        "Интимные зоны",
        "Диагностика"
    )
    var serviceTypesAlias: List<String> = listOf(
        "face",
        "body",
        "hair",
        "intim",
        "diagnostics"
    )


    lateinit var openServicesFragment: (position: Int) -> Unit
    lateinit var openServiceFragment: (position: Int, serviceTitle: String?, serviceTypeTitle: String?, parentPosition: Int, serviceId: String) -> Unit
    var services: MutableLiveData<MutableMap<String, MutableList<Service?>>> = MutableLiveData(mutableMapOf())
    var lastFragment = -1

    var imageList: List<Bitmap> = listOf()
}