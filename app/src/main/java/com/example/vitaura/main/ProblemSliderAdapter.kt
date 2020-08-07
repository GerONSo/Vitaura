package com.example.vitaura.main

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import com.example.vitaura.send_review.Problem
import com.example.vitaura.services.Service
import com.example.vitaura.services.ServiceRepository
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_problem.view.*
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*

class ProblemSliderAdapter(var imageList: List<Problem>) :
    SliderViewAdapter<ProblemSliderAdapter.SliderAdapterVH>() {
    private val IMAGE_URL = "https://vitaura-clinic.ru"
    var problemList: List<String?> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH =
        SliderAdapterVH(
            LayoutInflater.from(parent?.context).inflate(R.layout.card_problem, null)
        )

    override fun getCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        Picasso.get()
            .load(IMAGE_URL + imageList[position].photo)
            .into(viewHolder?.problemImageView)
        viewHolder?.problemTitleTextView?.text = imageList[position].title
        val alias = imageList[position].services
        viewHolder?.view?.setOnClickListener { view ->
            var list = alias.split(", ")
            var resultList: MutableList<Service?> = mutableListOf()
            for (i in list) {
                for (service in ServiceRepository.allServices.value!!) {
                    if(i == service?.tid) {
                        resultList.add(service)
                        break
                    }
                }
            }
            ServiceRepository.openServiceListFragment(resultList)
        }
    }

    inner class SliderAdapterVH(var view: View) : SliderViewAdapter.ViewHolder(view) {
        val problemImageView: ImageView = view.iv_problem
        val problemTitleTextView: TextView = view.tv_problem_title
    }

}