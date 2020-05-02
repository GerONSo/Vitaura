package com.example.vitaura.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.vitaura.R
import com.example.vitaura.doctors.DoctorsRepository
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*

class ImageSliderAdapter() : SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH>() {

    private val IMAGE_URL = "https://vitaura-clinic.ru"

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH =
        SliderAdapterVH(
            LayoutInflater.from(parent?.context).inflate(R.layout.image_slider_layout_item, null)
        )

    override fun getCount(): Int {
        return AboutDataRepository.getAboutImages().value?.size!!
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        Picasso.get()
            .load(IMAGE_URL + AboutDataRepository.getAboutImages().value?.get(position)!!)
            .into(viewHolder?.imageViewBackground)
    }

    inner class SliderAdapterVH(var view: View) : SliderViewAdapter.ViewHolder(view) {
        val imageViewBackground: ImageView = view.iv_auto_image_slider
    }

}