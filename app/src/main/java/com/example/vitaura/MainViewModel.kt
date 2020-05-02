package com.example.vitaura

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val fragmentStack: MutableLiveData<ArrayList<String>> by lazy {
        MutableLiveData<ArrayList<String>>().also {
            if(it.value == null) {
                it.value = arrayListOf()
            }
        }
    }

    fun getFragmentStack(): LiveData<ArrayList<String>> {
        return fragmentStack
    }

    fun addToFragmentStack(fragment: String) {
        fragmentStack.value = fragmentStack.value.also {
            it?.add(fragment)
        }
    }

    fun setFragmentStack(fragmentStack: ArrayList<String>) {
        this.fragmentStack.value = fragmentStack
    }

    fun removeLast() {
        if(fragmentStack.value?.size!! > 0){
            fragmentStack.value = fragmentStack.value.also {
                it?.removeAt(it.size - 1)
            }
        }
    }
}