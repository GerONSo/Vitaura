package com.example.vitaura.special

import androidx.lifecycle.MutableLiveData

object SpecialsRepository {
    private var specials: MutableLiveData<List<Special?>> = MutableLiveData()

    fun getSpecials(): MutableLiveData<List<Special?>> {
        return specials
    }

    fun setSpecials(newSpecials: List<Special?>) {
        specials.value = newSpecials
    }
}