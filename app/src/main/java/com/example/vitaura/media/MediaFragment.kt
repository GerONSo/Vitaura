package com.example.vitaura.media

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

import com.example.vitaura.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_media.*
import kotlinx.android.synthetic.main.fragment_send_review.*
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass.
 */
class MediaFragment : Fragment() {

    lateinit var toolbar: Toolbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        media_tab_layout.apply {
            addTab(media_tab_layout.newTab().setText("Фото"))
            addTab(media_tab_layout.newTab().setText("Видео"))
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(media_tab_layout.selectedTabPosition) {
                        0 -> {
                            MediaRepository.lastTab = MediaRepository.PHOTO_TAG
                            changeFragment(PhotoFragment(), MediaRepository.PHOTO_TAG)
                        }
                        1 -> {

                            MediaRepository.lastTab = MediaRepository.VIDEO_TAG
                            changeFragment(VideoFragment(), MediaRepository.VIDEO_TAG)
                        }
                    }
                }

            })
        }
        if(MediaRepository.lastTab == MediaRepository.PHOTO_TAG) {
            val tab = media_tab_layout.getTabAt(0)
            tab?.select()
            changeFragment(PhotoFragment(), MediaRepository.PHOTO_TAG)
        }
        else {
            val tab = media_tab_layout.getTabAt(1)
            tab?.select()
            changeFragment(VideoFragment(), MediaRepository.VIDEO_TAG)
        }
    }

    private fun changeFragment(newFragment: Fragment, tag: String) {
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_media, newFragment, tag)
            .commit()
    }

    fun updateUI() {
        toolbar = activity?.toolbar!!
        toolbar.setBackgroundColor(
            ContextCompat.getColor(requireContext(),
            R.color.colorAccent
        ))
        toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
        val title = activity?.toolbar_title
        title?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
        title?.text = "Фото и видео"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }
}
