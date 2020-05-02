package com.example.vitaura.special

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.special_card.view.*

class SpecialAdapter() : RecyclerView.Adapter<SpecialAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.special_card, parent, false))
    }

    override fun getItemCount(): Int {
        return SpecialsRepository.getSpecials().value?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load("https://vitaura-clinic.ru${SpecialsRepository.getSpecials().value?.get(position)?.imagePreviewPath}")
            .into(holder.backgroundImage)
        holder.titleText.text = Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position)?.name)
        holder.descriptionText.text = Html.fromHtml(SpecialsRepository.getSpecials().value?.get(position)?.body)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        val backgroundImage = view.background_image
        val titleText = view.title
        val descriptionText = view.special_description
    }

}