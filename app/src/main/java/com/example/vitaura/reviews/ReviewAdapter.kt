package com.example.vitaura.reviews

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import kotlinx.android.synthetic.main.card_review.view.*

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_review, parent, false))
    }

    override fun getItemCount(): Int {
        return if(ReviewRepository.getReviews().value != null){
            ReviewRepository.getReviews().value?.size!!
        } else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.accountImage.setImageResource(R.drawable.ic_account_circle_24dp)
        holder.name.text = ReviewRepository.getReviews().value?.get(position)?.title
        holder.text.text = Html.fromHtml(ReviewRepository.getReviews().value?.get(position)?.text)
    }


    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val accountImage = view.account_image!!
        val name = view.name!!
        val text = view.text!!
    }
}