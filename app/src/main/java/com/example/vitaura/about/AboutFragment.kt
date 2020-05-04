package com.example.vitaura.about


import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vitaura.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_about.*
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass.
 */

class AboutFragment : Fragment() {

    val TAB_ABOUT = "T1"
    val TAB_LICENSE = "T2"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val imageSlider: SliderView = view.findViewById(R.id.image_slider)
            val imageSliderAdapter = ImageSliderAdapter()
            imageSlider.setSliderAdapter(imageSliderAdapter)
            pb_image_slider.indeterminateDrawable.setColorFilter(
                resources.getColor(R.color.colorPrimary),
                PorterDuff.Mode.SRC_IN
            )
            AboutDataRepository.getAboutImages().observe(viewLifecycleOwner, Observer {
                imageSliderAdapter.notifyDataSetChanged()
                pb_image_slider.visibility = View.GONE
            })
        }

        val tabAboutFragment = TabAboutFragment()
        changeAboutFragment(tabAboutFragment, TAB_ABOUT)
        about_tab_layout.apply {
            addTab(about_tab_layout.newTab().setText("Информация"))
            addTab(about_tab_layout.newTab().setText("Лицензия"))
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (about_tab_layout.selectedTabPosition) {
                        0 -> {
                            val tabAboutFragment = TabAboutFragment()
                            changeAboutFragment(tabAboutFragment, TAB_ABOUT)
                        }
                        1 -> {
                            val tabLicenseFragment = TabLicenseFragment()
                            changeAboutFragment(tabLicenseFragment, TAB_LICENSE)
                        }
                    }
                }

            })
        }
    }

    fun updateUI() {
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.VISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.INVISIBLE
    }

    private fun changeAboutFragment(newFragment: Fragment, tag: String) {
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_about, newFragment, tag)
            .commit()
    }


}
