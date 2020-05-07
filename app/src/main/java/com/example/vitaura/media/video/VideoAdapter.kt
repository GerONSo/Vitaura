package com.example.vitaura.media.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import com.example.vitaura.media.MediaRepository
import kotlinx.android.synthetic.main.card_video.view.*

class VideoAdapter(val lifecycle: Lifecycle): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    companion object {
        var videoList: VideoData? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_video, parent, false))
    }

    override fun getItemCount(): Int = videoList?.data?.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(videoList != null && videoList?.data?.get(position)?.attrs?.link?.id != null) {
            holder.videoTitle.text = videoList?.data?.get(position)?.attrs?.title
            holder.view.setOnClickListener {
                MediaRepository.openYouTubePlayerFragment(
                    position
                )
            }
        }
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        var videoTitle: TextView = view.video_title
    }
}