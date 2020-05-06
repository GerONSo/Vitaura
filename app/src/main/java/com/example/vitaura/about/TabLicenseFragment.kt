package com.example.vitaura.about


import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.vitaura.MainRepository

import com.example.vitaura.R
import com.example.vitaura.doctors.DoctorsRepository
import com.example.vitaura.send_review.SendReviewFragment
import com.example.vitaura.send_review.SendReviewRepository
import com.example.vitaura.send_review.SendReviewViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_tab_license.*

/**
 * A simple [Fragment] subclass.
 */
class TabLicenseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_license, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AboutDataRepository.getLicenseTitles().observe(viewLifecycleOwner, Observer {
            tv_title_license1.text = it[0]
            tv_title_license2.text = it[1]
        })
        AboutDataRepository.getLicenseText().observe(viewLifecycleOwner, Observer {
            tv_text_license2.text = Html.fromHtml(it[0])
        })
        log_in_license_btn.setOnClickListener {
            MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
            AboutDataRepository.openSendReviewFragment()
        }
        license_layout1.setOnClickListener {
            if(iv_license1.visibility == View.GONE) {
                iv_license1.visibility = View.VISIBLE
            }
            else {
                iv_license1.visibility = View.GONE
            }
        }
        license_layout2.setOnClickListener {
            if(iv_license2.visibility == View.GONE) {
                iv_license2.visibility = View.VISIBLE
            }
            else {
                iv_license2.visibility = View.GONE
            }
        }
        license_layout3.setOnClickListener {
            if(iv_license3.visibility == View.GONE) {
                iv_license3.visibility = View.VISIBLE
            }
            else {
                iv_license3.visibility = View.GONE
            }
        }
        Picasso.get()
            .load("https://vitaura-clinic.ru/sites/default/files/files/vitaura_med.licenziya_1iz3.jpg")
            .into(iv_license1)
        Picasso.get()
            .load("https://vitaura-clinic.ru/sites/default/files/files/vitaura_med.licenziya_2iz3.jpg")
            .into(iv_license2)
        Picasso.get()
            .load("https://vitaura-clinic.ru/sites/default/files/files/vitaura_med.licenziya_3iz3.jpg")
            .into(iv_license3)
    }
}
