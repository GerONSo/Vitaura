package com.example.vitaura.media.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_change.view.*
import kotlinx.android.synthetic.main.card_gallery.view.*
import kotlinx.android.synthetic.main.card_gallery.view.iv_card_gallery

class GalleryChangeAdapter(var galleryData: ArrayList<ChangeFile>?): RecyclerView.Adapter<GalleryChangeAdapter.ViewHolder>() {

    val BASE_URL = "https://vitaura-clinic.ru"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_change, parent, false))
    }

    override fun getItemCount(): Int {
        return galleryData?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = galleryData?.get(position)?.path?.split(", ")
        Picasso.get()
            .load(BASE_URL + list?.get(0))
//            .load(R.drawable.d1)
            .resize(800, 800)
            .centerInside()
            .into(holder.galleryImageView)
        holder.titleTextView.text = galleryData?.get(position)?.title
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var galleryImageView: ImageView = view.iv_card_gallery

        var titleTextView: TextView = view.title
    }
}