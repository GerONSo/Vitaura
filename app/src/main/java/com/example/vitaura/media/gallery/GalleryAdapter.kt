package com.example.vitaura.media.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_gallery.view.*

class GalleryAdapter(var galleryData: ArrayList<String>?): RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    val BASE_URL = "https://vitaura-clinic.ru"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_gallery, parent, false))
    }

    override fun getItemCount(): Int {
        return galleryData?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load(BASE_URL + galleryData?.get(position))
//            .load(R.drawable.d1)
            .resize(1200, 1200)
            .centerInside()
            .into(holder.galleryImageView)
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var galleryImageView: ImageView = view.iv_card_gallery
    }
}