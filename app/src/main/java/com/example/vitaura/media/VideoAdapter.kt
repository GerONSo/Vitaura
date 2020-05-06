package com.example.vitaura.media

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.card_video.view.*

class VideoAdapter: RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

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
            holder.player.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoList?.data?.get(position)?.attrs?.link?.id!!, 0f)
                }
            })
            holder.view.setOnClickListener {
                if(holder.player.visibility == View.VISIBLE) {
                    holder.player.visibility = View.GONE
                }
                else {
                    holder.player.visibility = View.VISIBLE
                }
            }
        }
    }

    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        var videoTitle: TextView = view.video_title
        var player: YouTubePlayerView = view.player
    }
}