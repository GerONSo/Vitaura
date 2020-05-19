package com.example.vitaura.media

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MediaViewModel : ViewModel() {
    val lastSavedSecond: MutableLiveData<Float> by lazy { MutableLiveData(0f) }

    fun getLastSavedSecond(): LiveData<Float> {
        return lastSavedSecond
    }

    fun setLastSavedSecond(newValue: Float) {
        lastSavedSecond.value = newValue
    }
}