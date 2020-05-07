package com.example.vitaura.media.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import kotlinx.android.synthetic.main.card_gallery.view.*

class GalleryAdapter: RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_gallery, parent, false))
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var galleryImageView: ImageView = view.iv_card_gallery
    }
}