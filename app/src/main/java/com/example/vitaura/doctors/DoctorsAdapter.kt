package com.example.vitaura.doctors

import android.content.Context
import android.content.res.Resources
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ImageSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import com.example.vitaura.send_review.SendReviewViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_doctor.view.*

class DoctorsAdapter(
    val openDoctorFragment:(Int) -> Unit,
    val viewModel: DoctorViewModel
) : RecyclerView.Adapter<DoctorsAdapter.ViewHolder>() {

    var resources: Resources? = null
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        resources = parent.context.resources
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_doctor, parent, false))
    }

    override fun getItemCount(): Int {
        return if (DoctorsRepository.getDoctors().value != null) {
            DoctorsRepository.getDoctors().value?.size!!
        }
        else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = MainRepository.nodeDoctors.value?.data?.get(position)?.attrs?.title
        holder.loginButton.setOnClickListener {
            MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
            MainRepository.openSendReviewFragment()
        }
        var photoName = ""
        var specialization = ""
        for (doctor in DoctorsRepository.getDoctors().value!!) {
            if (doctor?.name?.replace("\n", " ") == MainRepository.nodeDoctors.value?.data?.get(position)?.attrs?.title) {
                photoName = doctor?.photoName!!
                specialization = doctor.spec
            }
        }
        holder.specTextView.text = specialization
        holder.portraitImageView.load("https://www.vitaura-clinic.ru/sites/default/files/${photoName}")
//        Picasso.get()
//            .load("https://vitaura-clinic.ru/sites/default/files/${DoctorsRepository.getDoctors().value?.get(position)?.photoName}")
//            .into(holder.portraitImageView)
        val miniDescription = MainRepository.nodeDoctors.value?.data?.get(position)?.attrs?.shortDescription?.value
            ?.replace("\n", "")
            ?.replace("</p>", "")
            ?.replace("<p>", "")
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
            DoctorsRepository.position = position
            MainRepository.openDoctorFragment(position)
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