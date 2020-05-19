package com.example.vitaura.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import kotlinx.android.synthetic.main.card_services.view.*

class ServicesAdapter(val servicesList: ArrayList<Services>,
                        val title: String) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_services, parent, false))
    }

    override fun getItemCount(): Int = servicesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.serviceTitleTextView.text = servicesList[position].data.attrs.name
        holder.view.setOnClickListener {
            ServiceRepository.openServiceFragment(position, title, servicesList[position].data.attrs.name)
        }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val serviceTitleTextView = view.tv_service_title
    }
}