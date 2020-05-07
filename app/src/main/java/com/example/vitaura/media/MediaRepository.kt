package com.example.vitaura.media

import androidx.lifecycle.MutableLiveData
import com.example.vitaura.media.gallery.Gallery
import com.example.vitaura.media.gallery.GalleryFileData
import com.example.vitaura.media.video.VideoData

object MediaRepository {
    val PHOTO_TAG = "T1"
    val VIDEO_TAG = "T2"
    val CLINIC_TAG = "C1"
    val CHANGE_TAG = "C2"
    val PRIZE_TAG = "C3"
    var linkList: MutableLiveData<VideoData> = MutableLiveData()
    var lastTab = PHOTO_TAG
    lateinit var openGalleryFragment: (tag: String) -> Unit
    lateinit var openYouTubePlayerFragment: (position: Int) -> Unit
    var clinicGallery: Gallery? = null
    var clinicFileData: GalleryFileData? = null
}