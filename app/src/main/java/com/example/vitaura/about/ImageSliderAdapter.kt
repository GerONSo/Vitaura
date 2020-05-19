package com.example.vitaura.about

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.vitaura.R
import com.example.vitaura.doctors.DoctorsRepository
import com.example.vitaura.main.SliderData
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*

class ImageSliderAdapter(var imageList: List<String?>,
                         var sliderData: SliderData? = null) : SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH>() {

    private val IMAGE_URL = "https://vitaura-clinic.ru"

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH =
        SliderAdapterVH(
            LayoutInflater.from(parent?.context).inflate(R.layout.image_slider_layout_item, null)
        )

    override fun getCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        Picasso.get()
            .load(IMAGE_URL + imageList[position])
            .into(viewHolder?.imageViewBackground)
        if(sliderData != null) {
            viewHolder?.titleTextView?.text = Html.fromHtml(sliderData?.data?.get(position)?.attrs?.title)
            viewHolder?.bodyTextView?.text = Html.fromHtml(sliderData?.data?.get(position)?.attrs?.body?.value)
        }
    }

    inner class SliderAdapterVH(var view: View) : SliderViewAdapter.ViewHolder(view) {
        val imageViewBackground: ImageView = view.iv_auto_image_slider
        val titleTextView = view.tv_slider_title
        val bodyTextView = view.tv_slider_body
    }

}