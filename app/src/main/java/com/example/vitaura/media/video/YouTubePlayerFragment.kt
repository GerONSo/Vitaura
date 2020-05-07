package com.example.vitaura.media.video

import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Html
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels

import com.example.vitaura.R
import com.example.vitaura.media.MediaViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_you_tube_player.*
import kotlinx.android.synthetic.main.fragment_you_tube_player.view.*

/**
 * A simple [Fragment] subclass.
 */
class YouTubePlayerFragment : Fragment() {

    lateinit var toolbar: Toolbar
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var upToolbar: RelativeLayout

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
        updateUI()
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
        toolbar.visibility = View.VISIBLE
        bottomNavigation.visibility = View.VISIBLE
        upToolbar.visibility = View.VISIBLE
        super.onDestroyView()
    }

    fun updateUI() {
        toolbar = activity?.toolbar!!
        bottomNavigation = activity?.bottom_navigation_view!!
        upToolbar = activity?.up_toolbar_layout!!
        toolbar.setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.colorAccent
            ))
        toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
        val title = activity?.toolbar_title
        title?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
        title?.text = "Фото и видео"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toolbar.visibility = View.GONE
            bottomNavigation.visibility = View.GONE
            upToolbar.visibility = View.GONE
        }
        else {
            toolbar.visibility = View.VISIBLE
            bottomNavigation.visibility = View.VISIBLE
            upToolbar.visibility = View.VISIBLE
        }
    }
}
