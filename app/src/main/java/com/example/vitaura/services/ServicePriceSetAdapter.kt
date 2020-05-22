package com.example.vitaura.services

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.vitaura.R
import com.example.vitaura.helpers.MutablePair
import com.example.vitaura.prices.*
import kotlinx.android.synthetic.main.card_price_set.view.*

class ServicePriceSetAdapter() : RecyclerView.Adapter<ServicePriceSetAdapter.PriceSetViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceSetViewHolder {
        context = parent.context
        return PriceSetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_price_set, parent, false))
    }

    override fun getItemCount(): Int {
        var cnt = 0
        for (i in 0 until 20) {
            if(getPriceSet(listOf(i, 0))?.first != null) {
                cnt++
            }
        }
        return cnt
    }

    override fun onBindViewHolder(holder: PriceSetViewHolder, pos: Int) {
        holder.view.setOnClickListener {
            if(holder.innerPriceRecyclerView.visibility == View.VISIBLE) {
                holder.innerPriceRecyclerView.visibility = View.GONE
                holder.plusImageView.setImageResource(R.drawable.ic_add_circle_24dp)
            }
            else {
                holder.innerPriceRecyclerView.visibility = View.VISIBLE
                holder.plusImageView.setImageResource(R.drawable.ic_remove_circle_24dp)
            }
        }
        var position = pos + 1
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        if(getPriceSet(listOf(position, 1)) == null) {
            val priceSubsetAdapter = ServicePriceAdapter(position)
            holder.innerPriceRecyclerView.apply {
                layoutManager = mLayoutManager
                adapter = priceSubsetAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }
        else {
            val prices: ArrayList<PriceSet> = arrayListOf()
            for(i in 1 until 20) {
                if(getPriceSet(listOf(position, i))?.second != null) {
                    prices.add(PriceSet(getPriceSet(listOf(position, i))?.first!!, getPriceSet(listOf(position, i))?.second!!))
                }
            }
            val priceSubsetAdapter = ServicePriceSubsetAdapter(prices, position)
            holder.innerPriceRecyclerView.apply {
                layoutManager = mLayoutManager
                adapter = priceSubsetAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }
        }
        holder.priceSetTextView.text = getPriceSet(listOf(position, 0))?.first
    }

    fun getPriceSet(pos: List<Int>): MutablePair<String, MutableList<Price>>? {
        return PricesRepository.servicePrices.value?.set?.get(pos)
    }


    class PriceSetViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val priceSetTextView: TextView = view.tv_price_set
        val innerPriceRecyclerView: RecyclerView = view.rv_price_inner
        val plusImageView: ImageView = view.plus_icon
    }

}