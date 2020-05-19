package com.example.vitaura.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.example.vitaura.R

object ServiceRepository {
    var serviceTypes: MutableLiveData<ServicesJSON> = MutableLiveData()
    lateinit var openServicesFragment: (position: Int) -> Unit
    lateinit var openServiceFragment: (position: Int, serviceTitle: String, serviceTypeTitle: String) -> Unit
    var services: MutableLiveData<ArrayList<Services>> = MutableLiveData(arrayListOf())

    var imageList: List<Bitmap> = listOf()
}