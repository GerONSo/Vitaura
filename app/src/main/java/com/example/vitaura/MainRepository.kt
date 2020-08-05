package com.example.vitaura

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.vitaura.doctors.Doctor
import com.example.vitaura.doctors.Doctors
import com.example.vitaura.doctors.DoctorsData
import com.example.vitaura.main.SliderData
import com.example.vitaura.prices.PriceData
import com.example.vitaura.prices.PriceElement
import com.example.vitaura.prices.Prices
import com.example.vitaura.send_review.ProblemData
import com.example.vitaura.send_review.SendReviewViewModel
import com.example.vitaura.services.Service

object MainRepository {
    val TAB_INFO = "T1"
    val TAB_REVIEW = "T2"
    var currentSendReviewTab = SendReviewViewModel.SEND_REVIEW
    lateinit var openSendReviewFragment: () -> Unit
    lateinit var openDoctorFragment: (position: Int) -> Unit
    lateinit var openServiceFragment: () -> Unit
    var sliderIds: MutableLiveData<SliderData> = MutableLiveData()
    var sliderImages: MutableLiveData<List<String?>> = MutableLiveData()
    var sliderMap: MutableLiveData<MutableMap<String, String>> = MutableLiveData(mutableMapOf())
    var sliderProblems: MutableLiveData<ProblemData> = MutableLiveData()
    var problemImageList: MutableLiveData<List<String?>> = MutableLiveData()
    var lastMainFragment = TAB_INFO

    var nodeDoctors: MutableLiveData<DoctorsData> = MutableLiveData()

    var nodePrices: MutableLiveData<PriceData> = MutableLiveData()
    var nidList: MutableLiveData<ArrayList<String>> = MutableLiveData(arrayListOf())

    var services: MutableLiveData<MutableList<MutableList<Service?>>> = MutableLiveData(mutableListOf())

    var serviceDoctorsMap: MutableLiveData<MutableMap<String, ArrayList<Doctors>>> = MutableLiveData(mutableMapOf())
    var servicePricesMap: MutableLiveData<MutableMap<String, ArrayList<PriceElement>>> = MutableLiveData(mutableMapOf())

    fun isUsedNid(nid: String): Boolean {
        nidList.value?.let {
            for (i in it) {
                if (i == nid) {
                    return true
                }
            }
        }
        return false
    }

    fun sortNodeDoctors() {
        nodeDoctors.value = nodeDoctors.value.also {
            it?.data = it?.data?.sortedBy { doctors ->
                doctors.attrs.weight
            }!!
        }
        nodeDoctors.value?.data?.forEach {
            Log.d("nodeDoctors", "${it.attrs.weight} ${it.attrs.title}")
        }

    }
}