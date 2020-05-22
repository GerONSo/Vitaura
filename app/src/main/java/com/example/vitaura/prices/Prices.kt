package com.example.vitaura.prices

import android.os.Parcel
import android.os.Parcelable
import com.example.vitaura.helpers.MutablePair
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Prices(@SerializedName("data") var set: MutableMap<List<Int>, MutablePair<String, MutableList<Price>>>)

class Price(@SerializedName("field_price_item") var name: String,
                 @SerializedName("field_price_sum") var value: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

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

data class PriceData(@field:Json(name = "data") var data: List<PriceElement>)

data class PriceElement(
    @field:Json(name = "attributes") var attrs: PriceAttributes,
    @field:Json(name = "relationships") var relationships: PriceRelationships
)

data class PriceAttributes(@field:Json(name = "drupal_internal__nid") var nid: String)

data class PriceRelationships(@field:Json(name = "field_service") var services: PriceServiceData)

data class PriceServiceData(@field:Json(name = "data") var data: List<PriceService>)

data class PriceService(@field:Json(name = "id") var id: String)