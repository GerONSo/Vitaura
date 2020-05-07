package com.example.vitaura.media

import androidx.lifecycle.MutableLiveData

object MediaRepository {
    val PHOTO_TAG = "T1"
    val VIDEO_TAG = "T2"
    var linkList: MutableLiveData<VideoData> = MutableLiveData()
    var lastTab = PHOTO_TAG
    lateinit var openGalleryFragment: () -> Unit
    lateinit var openYouTubePlayerFragment: (position: Int) -> Unit
}