package com.example.vitaura.special

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.vitaura.MainRepository

import com.example.vitaura.R
import com.example.vitaura.about.AboutDataRepository
import com.example.vitaura.about.ImageSliderAdapter
import com.example.vitaura.send_review.SendReviewViewModel
import com.smarteist.autoimageslider.SliderView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_action.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 */
class ActionFragment : Fragment() {

    var position: Int = 0
    var title: String? = null
    var body: String? = null

    fun setParams(newTitle: String?, newBody: String?, newPosition: Int) {
        title = newTitle
        body = newBody
        position = newPosition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_action, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_body_action.text = Html.fromHtml(body)
        tv_title_action.text = title
        MainRepository.sliderImages.observe(viewLifecycleOwner, Observer {
            val imageSlider: SliderView = view.findViewById(R.id.image_slider_action)
            val imageSliderAdapter = ImageSliderAdapter(it, MainRepository.sliderIds.value)
            imageSlider.setSliderAdapter(imageSliderAdapter)
            pb_image_slider_action.indeterminateDrawable.setColorFilter(
                resources.getColor(R.color.colorPrimary),
                PorterDuff.Mode.SRC_IN
            )
            imageSliderAdapter.notifyDataSetChanged()
            pb_image_slider_action.visibility = View.GONE
        })
        val actionSpecialCard1 = view.findViewById<View>(R.id.action_special_card_1)
        val actionSpecialCard2 = view.findViewById<View>(R.id.action_special_card_2)
        val backgroundImage1 = actionSpecialCard1.findViewById<ImageView>(R.id.background_image)
        val backgroundImage2 = actionSpecialCard2.findViewById<ImageView>(R.id.background_image)
        val position1 = (position + 1) % (SpecialsRepository.getSpecials().value?.size!!)
        val position2 = (position + 2) % (SpecialsRepository.getSpecials().value?.size!!)
        Picasso.get()
            .load("https://vitaura-clinic.ru${SpecialsRepository.getSpecials().value?.get(position1)?.imagePreviewPath}")
            .into(backgroundImage1)
        Picasso.get()
            .load("https://vitaura-clinic.ru${SpecialsRepository.getSpecials().value?.get(position2)?.imagePreviewPath}")
            .into(backgroundImage2)
        val titleText1 = actionSpecialCard1.findViewById<TextView>(R.id.title)
        val titleText2 = actionSpecialCard2.findViewById<TextView>(R.id.title)
        titleText1.text = Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position1)?.name)
        titleText2.text = Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position2)?.name)
        val descriptionText1 = actionSpecialCard1.findViewById<TextView>(R.id.special_description)
        val descriptionText2 = actionSpecialCard2.findViewById<TextView>(R.id.special_description)
        descriptionText1.text = Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position1)?.body)
        descriptionText2.text = Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position2)?.body)

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
        btn_login_action.setOnClickListener {
            MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
            AboutDataRepository.openSendReviewFragment()
        }

    }
}
