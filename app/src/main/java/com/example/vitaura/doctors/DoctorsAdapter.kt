package com.example.vitaura.doctors

import android.content.res.Resources
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.doctor_card_view.view.*

class DoctorsAdapter(val openDoctorFragment:(Int) -> Unit) : RecyclerView.Adapter<DoctorsAdapter.ViewHolder>() {

    var resources: Resources? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        resources = parent.context.resources
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.doctor_card_view, parent, false))
    }

    override fun getItemCount(): Int {
        return DoctorsRepository.getDoctors().value?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = DoctorsRepository.getDoctors().value?.get(position)?.name
        holder.specTextView.text = DoctorsRepository.getDoctors().value?.get(position)?.spec
        Picasso.get()
            .load("https://vitaura-clinic.ru/sites/default/files/${DoctorsRepository.getDoctors().value?.get(position)?.photoName}")
            .into(holder.portraitImageView)
        val miniDescription = DoctorsRepository.getDoctors().value?.get(position)?.miniDescription
        print(miniDescription)
        if(miniDescription?.length!! > 0 && miniDescription[0].isLetter()){
            holder.descriptionTextView.text = Html.fromHtml(miniDescription)
            holder.descriptionTextView.visibility = View.VISIBLE
        }
        else {
            holder.descriptionTextView.visibility = View.GONE
        }
        holder.view.setOnClickListener {
            openDoctorFragment(position)
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