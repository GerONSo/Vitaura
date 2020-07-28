package com.example.vitaura.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import kotlinx.android.synthetic.main.card_services.view.*

class ServicesAdapter(
    val servicesList: MutableList<Service?>,
    val title: String,
    val parentPosition: Int
) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_services, parent, false)
        )
    }

    override fun getItemCount(): Int = servicesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        servicesList[position]?.title.let {text ->
            holder.serviceTitleTextView.text = text
            holder.view.setOnClickListener {
                servicesList[position]?.id?.let { id ->
                    ServiceRepository.openServiceFragment(position, title, text, parentPosition, id)
                }
            }
        }
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val serviceTitleTextView = view.tv_service_title
    }
}