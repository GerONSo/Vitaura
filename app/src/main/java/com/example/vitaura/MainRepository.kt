package com.example.vitaura

import androidx.lifecycle.MutableLiveData
import com.example.vitaura.main.SliderData
import com.example.vitaura.send_review.SendReviewViewModel

object MainRepository {
    var currentSendReviewTab = SendReviewViewModel.SEND_REVIEW
    lateinit var openSendReviewFragment: () -> Unit
    var sliderIds: MutableLiveData<SliderData> = MutableLiveData()
    var sliderImages: MutableLiveData<List<String?>> = MutableLiveData()
    var sliderMap: MutableLiveData<MutableMap<String, String>> = MutableLiveData(mutableMapOf())
}