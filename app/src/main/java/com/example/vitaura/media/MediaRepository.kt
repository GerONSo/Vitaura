package com.example.vitaura.media

import androidx.lifecycle.MutableLiveData
import com.example.vitaura.media.gallery.ChangeFile
import com.example.vitaura.media.gallery.Gallery
import com.example.vitaura.media.gallery.GalleryFileData
import com.example.vitaura.media.video.VideoData
import org.jsoup.Jsoup

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
    var clinicFileData: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())
    var changePathsList: MutableLiveData<ArrayList<ChangeFile>> = MutableLiveData(arrayListOf())
    var prizeFileData: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())

    fun parseFileData(data: String) {
        val doc = Jsoup.parse(data)
        val elements = doc.getElementsByTag("img")
        val result: ArrayList<String> = arrayListOf()
        for(element in elements) {
            result.add(element.attr("src"))
        }
        prizeFileData.value = result
    }
}