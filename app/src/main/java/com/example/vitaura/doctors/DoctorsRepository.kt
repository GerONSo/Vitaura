package com.example.vitaura.doctors

import androidx.lifecycle.MutableLiveData

object DoctorsRepository {
    private var doctors: MutableLiveData<List<Doctor?>> = MutableLiveData()

    fun getDoctors(): MutableLiveData<List<Doctor?>> {
        return doctors
    }

    fun setDoctors(newDoctors: List<Doctor?>) {
        doctors.value = newDoctors
    }

}