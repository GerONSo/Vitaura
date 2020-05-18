package com.example.vitaura.prices

import android.util.Log
import com.example.vitaura.helpers.MutablePair
import com.google.gson.*
import java.lang.reflect.Type
import kotlin.collections.ArrayList


class PricesDeserializer : JsonDeserializer<Prices> {

    var array: MutableMap<List<Int>, MutablePair<String, MutableList<Price>>> = mutableMapOf()

    fun dfs(json: JsonElement, pos: MutableList<Int>, dep: Int) {
        var data = json
        if(data.javaClass.name != "com.google.gson.JsonObject") {
            if(data.javaClass.name == "com.google.gson.JsonArray") {
                val position = pos.toList()
                if(array[position] == null){
                    array[position] = MutablePair()
                }
                array[position]?.second = parseList(data)
            }
            return
        }
        pos[dep]++
        val curObject = data.asJsonObject
        val layer = curObject.entrySet()
        for(entry in layer) {
            val tmp = entry.value.asJsonObject
            val position = pos.toList()
            array[position] = MutablePair()
            array[position]?.first = tmp.get("name").asString
            dfs(tmp.get("data"), pos.toMutableList(), dep + 1)
            pos[dep]++
        }
    }

    fun parseList(data: JsonElement): MutableList<Price> {
        val array = data.asJsonArray
        var result = ArrayList<Price>()
        for(price in array) {
            val priceObject = price.asJsonObject
            val name = priceObject.get("field_price_item").asString
            val value = priceObject.get("field_price_sum").asString
            result.add(Price(name, value))
        }
        Log.d("data", result.toString())
        return result.toMutableList()
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Prices {
        val jsonObject = json?.asJsonObject
        val data = jsonObject?.get("data")!!
        dfs(data, mutableListOf(0, 0), 0)
        return Prices(array)
    }
}
