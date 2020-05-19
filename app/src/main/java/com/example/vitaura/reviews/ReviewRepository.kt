package com.example.vitaura.reviews

import androidx.lifecycle.MutableLiveData

object ReviewRepository {
    private var reviews: MutableLiveData<List<Review?>> = MutableLiveData()

    fun getReviews(): MutableLiveData<List<Review?>> {
        return reviews
    }

    fun setReviews(newReviews: List<Review>) {
        reviews.value = newReviews
    }
}