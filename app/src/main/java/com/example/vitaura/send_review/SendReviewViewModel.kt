package com.example.vitaura.send_review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SendReviewViewModel: ViewModel() {

    companion object {
        val LOGIN = "LOGIN"
        val SEND_REVIEW = "SEND_REVIEW"
    }

    private val name: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>().also {
            it.value = ""
        }
    }
    private val number: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>().also {
            it.value = ""
        }
    }
    private val review: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>().also {
            it.value = ""
        }
    }

    var lastSavedFragment: String? = SEND_REVIEW

    fun getName(): LiveData<String?> {
        return name
    }

    fun setName(newName: String?) {
        name.value = newName
    }

    fun getNumber(): LiveData<String?> {
        return number
    }

    fun setNumber(newNumber: String?) {
        number.value = newNumber
    }

    fun getReview(): LiveData<String?> {
        return review
    }

    fun setReview(newReview: String?) {
        review.value = newReview
    }

}