package com.example.vitaura.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import kotlinx.android.synthetic.main.card_service_submenu.view.*
import java.text.FieldPosition

class ServiceInnerAdapter (
    var services: MutableList<Service?>,
    var title: String,
    var parentPosition: Int
) : RecyclerView.Adapter<ServiceInnerAdapter.ServiceInnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceInnerViewHolder {
        return ServiceInnerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_service_submenu, parent, false)
        )
    }

    override fun getItemCount() = services.size

    override fun onBindViewHolder(holder: ServiceInnerViewHolder, position: Int) {
        holder.serviceNameTextView.text = services[position]?.title
        holder.serviceNameTextView.setOnClickListener {
            services[position]?.id?.let {
                ServiceRepository.openServiceFragment(
                    position,
                    services[position]?.title,
                    title,
                    parentPosition,
                    it,
                    services[position]
                )
            }
        }
    }

    inner class ServiceInnerViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        val serviceNameTextView = view.tv_service_name
    }
}