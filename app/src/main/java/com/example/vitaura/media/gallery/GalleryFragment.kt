package com.example.vitaura.media.gallery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.vitaura.R
import com.example.vitaura.media.MediaRepository
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
                val files = MediaRepository.clinicFileData?.data
                val ids = MediaRepository.clinicGallery?.relationships?.galleryImageField?.data
                val result: ArrayList<String> = arrayListOf()
                if(files != null && ids != null) {
                    for (file in files) {
                        Log.d("files", file.id)
                        for (galleryFile in ids) {
                            Log.d("files", "   " + galleryFile.id)
                            if(file.id == galleryFile?.attrs?.uri?.url) {
                                result.add(galleryFile?.attrs?.uri?.url)
                                break
                            }
                        }
                    }
                }

            }

            MediaRepository.CHANGE_TAG -> {
                // todo change
            }

            MediaRepository.PRIZE_TAG -> {
                // todo prize
            }
        }
    }
}
