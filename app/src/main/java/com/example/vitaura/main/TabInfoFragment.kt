package com.example.vitaura.main

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.vitaura.MainRepository

import com.example.vitaura.R
import com.example.vitaura.about.AboutDataRepository
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_tab_info.*
import kotlinx.android.synthetic.main.image_slider_popular.*

/**
 * A simple [Fragment] subclass.
 */
class TabInfoFragment : Fragment() {

    var problemAdapter: ProblemSliderAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AboutDataRepository.getAboutText().observe(viewLifecycleOwner, Observer {
            tv_main_info.text = Html.fromHtml(it[0])
        })
        val problemSlider = view.findViewById<SliderView>(R.id.image_slider_popular)
        problemSlider.indicatorRadius = 0
        MainRepository.sliderProblems.observe(viewLifecycleOwner, Observer {
            problemAdapter = ProblemSliderAdapter(it)
            problemSlider.setSliderAdapter(problemAdapter!!)
            problemAdapter?.notifyDataSetChanged()
        })
        MainRepository.problemImageList.observe(viewLifecycleOwner, Observer {
            problemAdapter?.problemList = it
        })
        btn_right_slide.setOnClickListener {
            problemSlider.slideToNextPosition()
        }
        btn_left_slide.setOnClickListener {
            problemSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_LEFT
            problemSlider.slideToNextPosition()
            problemSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        }
    }
}
