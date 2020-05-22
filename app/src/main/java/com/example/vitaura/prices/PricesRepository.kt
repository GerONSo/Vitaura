package com.example.vitaura.prices

import androidx.lifecycle.MutableLiveData
import com.example.vitaura.reviews.Review

object PricesRepository {
    private var prices: MutableLiveData<Prices> = MutableLiveData()
    var servicePrices: MutableLiveData<Prices> = MutableLiveData()

    fun getPrices(): MutableLiveData<Prices> {
        return prices
    }

    fun setPrices(newPrices: Prices) {
        prices.value = newPrices
    }
}