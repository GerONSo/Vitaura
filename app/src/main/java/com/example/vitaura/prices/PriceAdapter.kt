package com.example.vitaura.prices

import android.content.res.Resources
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import kotlinx.android.synthetic.main.card_price.view.*

class PriceAdapter(var topPosition: Int) : RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    lateinit var resources: Resources

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        resources = parent.resources
        return PriceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_price, parent, false))
    }

    override fun getItemCount(): Int {
        return getPriceSet(listOf(topPosition, 0))?.size!!
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        var name = getPriceSet(listOf(topPosition, 0))?.get(position)?.name
        val price = getPriceSet(listOf(topPosition, 0))?.get(position)?.value
        holder.priceNameTextView.text = name
        holder.priceTextView.text = price
        if(price == "" && name?.get(0) == '*') {
            name = name.slice(1 until name.length)
            holder.priceNameTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.textColor, null))
            holder.priceNameTextView.text = name
        }
        else if(price == "") {
            holder.priceNameTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.textColor, null))
            holder.priceNameTextView.typeface = Typeface.DEFAULT_BOLD
            holder.priceNameTextView.isAllCaps = true
        }
    }

    fun getPriceSet(pos: List<Int>): MutableList<Price>? {
        return PricesRepository.getPrices().value?.set?.get(pos)?.second
    }

    inner class PriceViewHolder(val view: View) : ChildViewHolder(view) {
        val priceNameTextView: TextView = view.tv_price_name
        val priceTextView: TextView = view.tv_price
    }

}