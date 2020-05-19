package com.example.vitaura.services

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_service_types.view.*
import java.util.*

class ServiceTypesAdapter(val resources: Resources,
                          var serviceTypesList: ServicesJSON): RecyclerView.Adapter<ServiceTypesAdapter.ViewHolder>() {

    private val imageList: List<Bitmap> = listOf(
        BitmapFactory.decodeResource(resources, R.drawable.face),
        BitmapFactory.decodeResource(resources, R.drawable.body),
        BitmapFactory.decodeResource(resources, R.drawable.hair),
        BitmapFactory.decodeResource(resources, R.drawable.intim)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_service_types, parent, false))
    }

    override fun getItemCount(): Int = serviceTypesList.data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position < imageList.size) {
            holder.serviceTypeImageView.setImageBitmap(imageList[position])
        }
        holder.serviceTypeTextView.text = serviceTypesList.data[position].attrs.name
        holder.view.setOnClickListener {
            ServiceRepository.openServicesFragment(position)
        }
    }


    inner class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        val serviceTypeImageView: ImageView = view.iv_service_types_circle
        val serviceTypeTextView: TextView = view.tv_service_type
    }
}