package com.example.vitaura.media.video

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.vitaura.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_video.*

/**
 * A simple [Fragment] subclass.
 */
class VideoFragment : Fragment() {

    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv_video.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = VideoAdapter(lifecycle)
        }
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
