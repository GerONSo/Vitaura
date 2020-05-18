package com.example.vitaura.prices

import android.os.Parcel
import android.os.Parcelable
import com.example.vitaura.helpers.MutablePair
import com.google.gson.annotations.SerializedName

data class Prices(@SerializedName("data") var set: MutableMap<List<Int>, MutablePair<String, MutableList<Price>>>)

class Price(@SerializedName("field_price_item") var name: String,
                 @SerializedName("field_price_sum") var value: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Price> {
        override fun createFromParcel(parcel: Parcel): Price {
            return Price(parcel)
        }

        override fun newArray(size: Int): Array<Price?> {
            return arrayOfNulls(size)
        }
    }

}