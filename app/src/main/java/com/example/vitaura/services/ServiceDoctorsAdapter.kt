package com.example.vitaura.services

import android.content.Context
import android.content.res.Resources
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.helpers.HtmlNormalizer
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import com.example.vitaura.doctors.*
import com.example.vitaura.send_review.SendReviewViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_doctor.view.*

class ServiceDoctorsAdapter(val doctorsList: ArrayList<Doctors>?) :
    RecyclerView.Adapter<ServiceDoctorsAdapter.ViewHolder>() {

    var resources: Resources? = null
    lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        resources = parent.context.resources
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_doctor, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return doctorsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = doctorsList?.get(position)?.attrs?.title
        holder.specTextView.text = doctorsList?.get(position)?.attrs?.post
        holder.loginButton.setOnClickListener {
            MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
            MainRepository.openSendReviewFragment()
        }
        var photoName: String? = null
        for (doctor in DoctorsRepository.getDoctors().value!!) {
//            Log.d("doctor", doctor?.name?.toUpperCase()?.replace("\n", " "))
//            Log.d("doctor", doctorsList?.get(position)?.attrs?.title?.toUpperCase())
            if(doctor?.name?.toUpperCase()?.replace("\n", " ")?.replace(" ", "")
                == doctorsList?.get(position)?.attrs?.title?.toUpperCase()?.replace(" ", "")) {
                photoName = doctor?.photoName
            }
        }
        Picasso.get()
            .load(
                "https://vitaura-clinic.ru/sites/default/files/${photoName}"
            )
            .into(holder.portraitImageView)
        val miniDescription = HtmlNormalizer.normalize(doctorsList?.get(position)?.attrs?.shortDescription?.value)
        if(miniDescription != null && miniDescription.isNotEmpty()){
            var spannableString = SpannableString(" ")
            spannableString.setSpan(ImageSpan(context, R.drawable.arrow_next1), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val result: CharSequence = TextUtils.concat(SpannableString(Html.fromHtml(miniDescription)), spannableString)
            holder.descriptionTextView.text = result
            holder.descriptionTextView.visibility = View.VISIBLE
        }
        else {
            holder.descriptionTextView.visibility = View.GONE
        }
        holder.view.setOnClickListener {
            for (i in 0 until DoctorsRepository.getDoctors().value?.size!!) {
                if(DoctorsRepository.getDoctors().value?.get(i)?.name?.toUpperCase()?.replace("\n", " ")?.replace(" ", "")
                    == doctorsList?.get(position)?.attrs?.title?.toUpperCase()?.replace(" ", "")) {
                    MainRepository.openDoctorFragment(i)
                }
            }
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        val portraitImageView = view.portrait_iv!!
        val nameTextView = view.name_tv!!
        val specTextView = view.spec_tv!!
        val loginButton = view.log_in_btn!!
        val descriptionTextView = view.description!!
    }
}