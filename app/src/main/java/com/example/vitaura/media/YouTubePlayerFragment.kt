package com.example.vitaura.media

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.example.vitaura.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import kotlinx.android.synthetic.main.fragment_you_tube_player.*
import kotlinx.android.synthetic.main.fragment_you_tube_player.view.*

/**
 * A simple [Fragment] subclass.
 */
class YouTubePlayerFragment : Fragment() {

    companion object {
        var position = 0

        fun newInstance(newPosition: Int): YouTubePlayerFragment {
            position = newPosition
            return YouTubePlayerFragment()
        }
    }

    val tracker = YouTubePlayerTracker()
    val viewModel: MediaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_you_tube_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val playerView = view.player
        lifecycle.addObserver(playerView)
        playerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(VideoAdapter.videoList?.data?.get(position)?.attrs?.link?.id!!, viewModel.getLastSavedSecond().value!!)
            }
        })
        playerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.addListener(tracker)
            }

        })
        val text = VideoAdapter.videoList?.data?.get(position)?.attrs?.body?.value
        val title = VideoAdapter.videoList?.data?.get(position)?.attrs?.title
        if(text != null) {
            tv_video_description.text = Html.fromHtml(text)
        }
        if(title != null) {
            tv_video_title.text = title
        }
    }

    override fun onDestroyView() {
        viewModel.setLastSavedSecond(tracker.currentSecond)
        super.onDestroyView()
    }
}
