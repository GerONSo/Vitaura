package com.example.vitaura.prices

import android.content.res.Resources
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.vitaura.helpers.MutablePair
import com.example.vitaura.R
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.card_price.view.*
import kotlinx.android.synthetic.main.card_price_subset.view.*
import java.util.*

class PriceSubsetAdapter(groups: List<PriceSet>, var topPosition: Int) :
    ExpandableRecyclerViewAdapter<PriceSubsetAdapter.PriceSubsetViewHolder, PriceSubsetAdapter.PriceViewHolder>(
        groups
    ) {

    lateinit var resources: Resources
    var isOpen: MutableList<Int> = Collections.nCopies(groups.size, 0).toMutableList()

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): PriceSubsetViewHolder {
        resources = parent?.resources!!
        return PriceSubsetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_price_subset, parent, false))
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): PriceViewHolder {
        return PriceViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_price, parent, false))
    }

    override fun onBindChildViewHolder(
        holder: PriceViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {
        val price = group?.items?.get(childIndex) as Price
        holder?.priceNameTextView?.text = price.name
        holder?.priceTextView?.text = price.value
        holder?.priceNameTextView?.setTextColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))
        val value = price.value
        var name = price.name
        if(value == "" && name[0] == '*') {
            name = name.slice(1 until name.length)
            holder?.priceNameTextView?.setTextColor(ResourcesCompat.getColor(resources, R.color.textColor, null))
            holder?.priceNameTextView?.text = name
        }
        else if(value == "") {
            holder?.priceNameTextView?.setTextColor(ResourcesCompat.getColor(resources, R.color.textColor, null))
            holder?.priceNameTextView?.typeface = Typeface.DEFAULT_BOLD
            holder?.priceNameTextView?.isAllCaps = true
        }
    }

    override fun onBindGroupViewHolder(
        holder: PriceSubsetViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {

        val text = group?.title
        holder?.priceSetTextView?.text = text
        holder?.setOnGroupClickListener {
            if(isOpen[flatPosition] == 1) {
                holder.arrowImageView.setImageResource(R.drawable.ic_arrow_drop_down)
            }
            else {
                holder.arrowImageView.setImageResource(R.drawable.ic_arrow_drop_up)
            }
            isOpen[flatPosition] = isOpen[flatPosition] xor 1
            super.onGroupClick(it)
        }
    }

    fun getPriceSet(pos: List<Int>): MutablePair<String, MutableList<Price>>? {
        return PricesRepository.getPrices().value?.set?.get(pos)
    }


    inner class PriceSubsetViewHolder(val view: View) : GroupViewHolder(view) {
        val priceSetTextView: TextView = view.tv_price_subset
        val arrowImageView: ImageView = view.iv_arrow
    }

    inner class PriceViewHolder(val view: View) : ChildViewHolder(view) {
        val priceNameTextView: TextView = view.tv_price_name
        val priceTextView: TextView = view.tv_price
    }
}