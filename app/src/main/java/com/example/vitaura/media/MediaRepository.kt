package com.example.vitaura.media

import androidx.lifecycle.MutableLiveData

object MediaRepository {
    var linkList: MutableLiveData<VideoData> = MutableLiveData()
    lateinit var openGalleryFragment: () -> Unit

}