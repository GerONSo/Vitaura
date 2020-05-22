package com.example.vitaura

import androidx.lifecycle.MutableLiveData
import com.example.vitaura.doctors.DoctorsData
import com.example.vitaura.main.SliderData
import com.example.vitaura.prices.PriceData
import com.example.vitaura.send_review.ProblemData
import com.example.vitaura.send_review.SendReviewViewModel

object MainRepository {
    val TAB_INFO = "T1"
    val TAB_REVIEW = "T2"
    var currentSendReviewTab = SendReviewViewModel.SEND_REVIEW
    lateinit var openSendReviewFragment: () -> Unit
    lateinit var openDoctorFragment: (position: Int) -> Unit
    var sliderIds: MutableLiveData<SliderData> = MutableLiveData()
    var sliderImages: MutableLiveData<List<String?>> = MutableLiveData()
    var sliderMap: MutableLiveData<MutableMap<String, String>> = MutableLiveData(mutableMapOf())
    var sliderProblems: MutableLiveData<ProblemData> = MutableLiveData()
    var problemImageList: MutableLiveData<List<String?>> = MutableLiveData()
    var lastMainFragment = TAB_INFO

    var nodeDoctors: MutableLiveData<DoctorsData> = MutableLiveData()

    var nodePrices: MutableLiveData<PriceData> = MutableLiveData()
    var nidList: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())

    fun isUsedNid(nid: String): Boolean {
        for(i in nidList.value!!) {
            if(i == nid) {
                return true
            }
        }
        return false
    }
}