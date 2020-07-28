package com.example.vitaura.about


import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.vitaura.MainRepository

import com.example.vitaura.R
import com.example.vitaura.helpers.HtmlNormalizer
import com.example.vitaura.send_review.SendReviewRepository
import com.example.vitaura.send_review.SendReviewViewModel
import com.example.vitaura.special.SpecialsRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_tab_about.*

/**
 * A simple [Fragment] subclass.
 */
class TabAboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log_in_about_btn.setOnClickListener {
            MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
            AboutDataRepository.openSendReviewFragment()
        }
        AboutDataRepository.getAboutText().observe(viewLifecycleOwner, Observer {
            tv_text_about1.text = Html.fromHtml(HtmlNormalizer.normalizeAbout(it[0]))
            tv_text_about2.text = Html.fromHtml(HtmlNormalizer.normalizeAbout(it[1]))
            tv_text_about3.text = Html.fromHtml(HtmlNormalizer.normalizeAbout(it[2]))
            Log.d("about", it[1])
            Log.d("about", HtmlNormalizer.normalizeAbout(it[1]))
        })
        AboutDataRepository.getAboutTitles().observe(viewLifecycleOwner, Observer {
            tv_title_about1.text = it[0]
            tv_title_about2.text = it[1]
            tv_title_about3.text = it[2]
        })
        SpecialsRepository.getSpecials().observe(viewLifecycleOwner, Observer {
            val actionSpecialCard1 = view.findViewById<View>(R.id.about_special_card_1)
            val actionSpecialCard2 = view.findViewById<View>(R.id.about_special_card_2)
            val backgroundImage1 = actionSpecialCard1.findViewById<ImageView>(R.id.background_image)
            val backgroundImage2 = actionSpecialCard2.findViewById<ImageView>(R.id.background_image)
            val position1 = 0
            val position2 = 1
            Picasso.get()
                .load(
                    "https://vitaura-clinic.ru${SpecialsRepository.getSpecials().value?.get(
                        position1
                    )?.imagePreviewPath}"
                )
                .into(backgroundImage1)
            Picasso.get()
                .load(
                    "https://vitaura-clinic.ru${SpecialsRepository.getSpecials().value?.get(
                        position2
                    )?.imagePreviewPath}"
                )
                .into(backgroundImage2)
            val titleText1 = actionSpecialCard1.findViewById<TextView>(R.id.title)
            val titleText2 = actionSpecialCard2.findViewById<TextView>(R.id.title)
            titleText1.text =
                Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position1)?.name)
            titleText2.text =
                Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position2)?.name)
            val descriptionText1 =
                actionSpecialCard1.findViewById<TextView>(R.id.special_description)
            val descriptionText2 =
                actionSpecialCard2.findViewById<TextView>(R.id.special_description)
            descriptionText1?.let {
                it.text =
                    Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position1)?.body)
            }
            descriptionText2?.let {
                it.text =
                    Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position2)?.body)
            }

            actionSpecialCard1.setOnClickListener {
                val title = SpecialsRepository.getSpecialTitle(position1)
                val body = SpecialsRepository.getSpecialBody(position1)
                SpecialsRepository.openActionFragment(title, body, position1)
            }
            actionSpecialCard2.setOnClickListener {
                val title = SpecialsRepository.getSpecialTitle(position2)
                val body = SpecialsRepository.getSpecialBody(position2)
                SpecialsRepository.openActionFragment(title, body, position2)
            }
        })
    }
}
