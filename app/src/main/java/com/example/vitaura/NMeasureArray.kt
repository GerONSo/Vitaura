package com.example.vitaura

import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.ArrayList

class NMeasureArray<T>(initValues: List<T?>) : ArrayList<T>(initValues) {

    var suff: MutableList<Int> = mutableListOf()
    var measures: List<Int> = mutableListOf()

    fun get(pos: List<Int>): T {
        return super.get(getLinearPosition(pos))
    }

    fun set(index: List<Int>, element: T): T {
        return super.set(getLinearPosition(index), element)
    }

    private fun getLinearPosition(position: List<Int>) : Int {
        var result = 0
        for(i in 0 until position.size - 1) {
            result += position[i] * suff[i + 1]
        }
        return result
    }

    class NMeasureArrayFactory<T> {


        var instance: NMeasureArray<T>? = null

        fun build(measures: List<Int>): NMeasureArray<T> {
            if(measures.size < 2) {
                throw IllegalStateException("Cannot provide multimeasure arrays for less than 2 measures")
            }
            var size = 1
            measures.forEach {
                size *= it
            }
            instance = NMeasureArray(Collections.nCopies(size, null))
            instance?.measures = measures
            instance?.suff = measures.toMutableList()
            for(i in measures.size - 2 downTo 0) {
                instance?.suff?.get(i + 1)?.let { instance?.suff?.set(i, it)}
            }
            return instance!!
        }

    }
}