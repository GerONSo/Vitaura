package com.example.vitaura.main

import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import com.example.vitaura.about.AboutDataRepository
import com.example.vitaura.about.ImageSliderAdapter
import com.example.vitaura.about.TabAboutFragment
import com.example.vitaura.about.TabLicenseFragment
import com.example.vitaura.reviews.ReviewFragment
import com.google.android.material.tabs.TabLayout
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_tab_info.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            MainRepository.sliderImages.observe(viewLifecycleOwner, Observer {
                val imageSlider: SliderView = view.findViewById(R.id.image_slider_main)
                val imageSliderAdapter = ImageSliderAdapter(it, MainRepository.sliderIds.value)

                imageSlider.setSliderAdapter(imageSliderAdapter)
                pb_image_slider_main.indeterminateDrawable.setColorFilter(
                    resources.getColor(R.color.colorPrimary),
                    PorterDuff.Mode.SRC_IN
                )
                imageSliderAdapter.notifyDataSetChanged()
                pb_image_slider_main.visibility = View.GONE
            })
        }
        main_tab_layout.apply {
            addTab(main_tab_layout.newTab().setText("Информация"))
            addTab(main_tab_layout.newTab().setText("Отзывы"))
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (main_tab_layout.selectedTabPosition) {
                        0 -> {
                            val tabInfoFragment = TabInfoFragment()
                            changeFragment(tabInfoFragment, MainRepository.TAB_INFO)
                        }
                        1 -> {
                            val tabReviewFragment = ReviewFragment()
                            changeFragment(tabReviewFragment, MainRepository.TAB_REVIEW)
                        }
                    }
                }
            })
        }
        if(MainRepository.lastMainFragment == MainRepository.TAB_INFO) {
            val tab = main_tab_layout.getTabAt(0)
            tab?.select()
            val tabInfoFragment = TabInfoFragment()
            changeFragment(tabInfoFragment, MainRepository.TAB_INFO)
        }
        else {
            val tab = main_tab_layout.getTabAt(1)
            tab?.select()
            val tabReviewFragment = ReviewFragment()
            changeFragment(tabReviewFragment, MainRepository.TAB_REVIEW)
        }
    }

    private fun changeFragment(newFragment: Fragment, tag: String) {
        MainRepository.lastMainFragment = tag
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_main, newFragment, tag)
            .commit()
    }
}
