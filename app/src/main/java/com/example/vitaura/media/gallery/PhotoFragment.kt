package com.example.vitaura.media.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.vitaura.R
import com.example.vitaura.media.MediaRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photo.*

/**
 * A simple [Fragment] subclass.
 */
class PhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Picasso.get()
            .load("https://vitaura-clinic.ru/sites/default/files/inline-images/_MG_0031.jpg")
            .into(iv_clinic)
        Picasso.get()
            .load("https://vitaura-clinic.ru/sites/default/files/inline-images/pp110220-01.jpg")
            .into(iv_change)
        Picasso.get()
            .load("https://vitaura-clinic.ru/sites/default/files/inline-images/_MG_2215%20copy.jpg")
            .into(iv_prize)
        title.text = "Фото клиники"
        title_2.text = "Фото до и после"
        title_3.text = "Премия Грация"
        special_description.text = "Фотогалерея нашей клиники аппаратной косметологии и эстетической медицины VITAURA."
        special_description_2.text = "Наше портфолио. Примеры работ и результатов «до» и «после» проведения процедур специалистами нашей клиники."
        special_description_3.text = "Победа в номинации \"Лучшая клиника эстетической медицины\""

        card_clinic.setOnClickListener {
            MediaRepository.openGalleryFragment(MediaRepository.CLINIC_TAG)
        }
        card_change.setOnClickListener {
            MediaRepository.openGalleryFragment(MediaRepository.CHANGE_TAG)
        }
        card_prize.setOnClickListener {
            MediaRepository.openGalleryFragment(MediaRepository.PRIZE_TAG)
        }
    }
}
