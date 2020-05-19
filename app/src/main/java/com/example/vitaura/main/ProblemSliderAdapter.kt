package com.example.vitaura.main

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.vitaura.R
import com.example.vitaura.send_review.ProblemData
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_problem.view.*
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*

class ProblemSliderAdapter(var imageList: ProblemData) : SliderViewAdapter<ProblemSliderAdapter.SliderAdapterVH>() {
    private val IMAGE_URL = "https://vitaura-clinic.ru"
    var problemList: List<String?> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH =
        SliderAdapterVH(
            LayoutInflater.from(parent?.context).inflate(R.layout.card_problem, null)
        )

    override fun getCount(): Int {
        return imageList.data.size
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        if(position < problemList.size && problemList[position] != null) {
            Picasso.get()
                .load(IMAGE_URL + problemList[position])
                .into(viewHolder?.problemImageView)
        }
        Log.d("problem", imageList.data[position].attrs.title)
        viewHolder?.problemTitleTextView?.text = imageList.data[position].attrs.title
    }

    inner class SliderAdapterVH(var view: View) : SliderViewAdapter.ViewHolder(view) {
        val problemImageView: ImageView = view.iv_problem
        val problemTitleTextView: TextView = view.tv_problem_title
    }

}