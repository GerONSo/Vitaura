package com.example.vitaura.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import kotlinx.android.synthetic.main.card_price_subset.view.*
import kotlinx.android.synthetic.main.card_services.view.*

class ServicesAdapter(
    val servicesList: MutableList<Service?>,
    val title: String,
    val parentPosition: Int,
    val services: MutableList<Service?>
) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    var isOpened: MutableList<Boolean> = mutableListOf()

    init {
        for (i in servicesList) {
            isOpened.add(false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_services, parent, false)
        )
    }

    override fun getItemCount(): Int = servicesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        servicesList[position]?.title.let { text ->
            holder.serviceTitleTextView.text = text
            var list: MutableList<Service?> = mutableListOf()
            for (service in services) {
                if (service?.parentTid == servicesList[position]?.tid) {
                    list.add(service)
                }
            }
            holder.innerRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = ServiceInnerAdapter(list, title, parentPosition)
            }
            holder.view.setOnClickListener {
                if(list.size == 0) {
                    servicesList[position]?.id?.let {
                        ServiceRepository.openServiceFragment(
                            position,
                            servicesList[position]?.title,
                            title,
                            parentPosition,
                            it,
                            servicesList[position]
                        )
                    }
                }
                else {
                    isOpened[position] = !isOpened[position]
                    if (isOpened[position]) {
//                    holder.iconArrowImageView.setImageResource(R.drawable.ic_arrow_drop_up)
                        holder.innerRecyclerView.visibility = View.VISIBLE
                    } else {
                        holder.innerRecyclerView.visibility = View.GONE
                    }
                }
//                servicesList[position]?.id?.let { id ->
//                    ServiceRepository.openServiceFragment(position, title, text, parentPosition, id)
//                }
            }
        }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val serviceTitleTextView = view.tv_service_title
        val innerRecyclerView = view.rv_service_submenu
        val iconArrowImageView = view.iv_arrow
    }
}