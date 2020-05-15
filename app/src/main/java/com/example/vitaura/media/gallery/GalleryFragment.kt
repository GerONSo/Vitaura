package com.example.vitaura.media.gallery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.vitaura.R
import com.example.vitaura.ServerHelper
import com.example.vitaura.media.MediaRepository
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : Fragment() {

    companion object {

        lateinit var galleryTag: String

        fun newInstance(newTag: String): GalleryFragment {
            galleryTag = newTag
            return GalleryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when(galleryTag) {
            MediaRepository.CLINIC_TAG -> {
                val files = MediaRepository.clinicGallery?.relationships?.galleryImageField?.data
                if(files != null) {
                    for (file in files) {
                        ServerHelper.getFile(file.id)
                    }
                }
                var result: ArrayList<String> = arrayListOf()
                MediaRepository.clinicFileData.observe(viewLifecycleOwner, Observer {
                    result = it
                    val galleryAdapter = GalleryAdapter(result)
                    rv_gallery.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        adapter = galleryAdapter
                    }
                    galleryAdapter.notifyDataSetChanged()
                })

            }

            MediaRepository.CHANGE_TAG -> {
                MediaRepository.changePathsList.observe(viewLifecycleOwner, Observer {
                    val galleryAdapter = GalleryChangeAdapter(it)
                    rv_gallery.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        adapter = galleryAdapter
                    }
                    galleryAdapter.notifyDataSetChanged()
                })
            }

            MediaRepository.PRIZE_TAG -> {
                MediaRepository.prizeFileData.observe(viewLifecycleOwner, Observer {
                    val galleryAdapter = GalleryAdapter(it)
                    rv_gallery.apply {
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        adapter = galleryAdapter
                    }
                })
            }
        }
    }
}
