package com.example.vitaura.special

import androidx.lifecycle.MutableLiveData

object SpecialsRepository {
    private var specials: MutableLiveData<List<Special?>> = MutableLiveData()
    var actions: MutableLiveData<ActionsData> = MutableLiveData()
    lateinit var openActionFragment: (title: String?, body: String?, position: Int) -> Unit

    fun getSpecials(): MutableLiveData<List<Special?>> {
        return specials
    }

    fun setSpecials(newSpecials: List<Special?>) {
        specials.value = newSpecials
    }

    fun  getSpecialTitle(position: Int): String? {
        return specials.value?.get(position)?.name
    }

    fun getSpecialBody(position: Int): String {
        actions.value?.data?.let {
            for (action in it) {
                if (action.attrs.path.alias == specials.value?.get(position)?.id) {
                    return action.attrs.body.value
                }
            }
        }
        return ""
    }
}