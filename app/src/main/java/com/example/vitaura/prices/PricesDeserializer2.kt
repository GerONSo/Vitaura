package com.example.vitaura.prices

import android.util.Log
import com.example.vitaura.MainRepository
import com.example.vitaura.helpers.MutablePair
import com.google.gson.*
import java.lang.reflect.Type
import kotlin.collections.ArrayList


class PricesDeserializer2 : JsonDeserializer<Prices> {

    var array: MutableMap<List<Int>, MutablePair<String, MutableList<Price>>> = mutableMapOf()

    fun dfs(json: JsonElement, pos: MutableList<Int>, dep: Int): Boolean {
        var data = json
        if(data.javaClass.name != "com.google.gson.JsonObject") {
            if(data.javaClass.name == "com.google.gson.JsonArray") {
                val position = pos.toList()
                if(array[position] == null) {
                    array[position] = MutablePair()
                }
                val tmp = parseList(data)
                return if(tmp.size > 0) {
                    array[position]?.second = tmp
                    true
                } else {
                    false
                }
            }
            return false
        }
        pos[dep]++
        var flag = false
        val curObject = data.asJsonObject
        val layer = curObject.entrySet()
        for(entry in layer) {
            val tmp = entry.value.asJsonObject
            val position = pos.toList()
            array[position] = MutablePair()
            array[position]?.first = tmp.get("name").asString
            if(dfs(tmp.get("data"), pos.toMutableList(), dep + 1)) {
                pos[dep]++
                flag = true
            }
            else {
                array.remove(position)
            }
        }
        return flag
    }

    fun parseList(data: JsonElement): MutableList<Price> {
        val array = data.asJsonArray
        var result = ArrayList<Price>()
        for(price in array) {
            val priceObject = price.asJsonObject
            val name = priceObject.get("field_price_item").asString
            val value = priceObject.get("field_price_sum").asString
            val nid = priceObject.get("nid").asString
            if(MainRepository.isUsedNid(nid)) {
                result.add(Price(name, value))
            }
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
