package com.example.vitaura.special

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_special.view.*

class SpecialAdapter() : RecyclerView.Adapter<SpecialAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_special, parent, false))
    }

    override fun getItemCount(): Int {
        SpecialsRepository.getSpecials().value?.size?.let {
            return it
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load("https://vitaura-clinic.ru${SpecialsRepository.getSpecials().value?.get(position)?.imagePreviewPath}")
            .into(holder.backgroundImage)
        holder.titleText.text = Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position)?.name)
        holder.descriptionText?.text = Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position)?.body)
        holder.view.setOnClickListener {
            val title = SpecialsRepository.getSpecialTitle(position)
            val body = SpecialsRepository.getSpecialBody(position)
            SpecialsRepository.openActionFragment(title, body, position)
        }
        holder.cardView.preventCornerOverlap = false
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        val backgroundImage = view.background_image
        val titleText = view.title
        val descriptionText = view.special_description
        val cardView = view.card_special
    }

}