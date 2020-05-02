package com.example.vitaura.about


import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.vitaura.R
import com.example.vitaura.SendReviewFragment
import kotlinx.android.synthetic.main.fragment_tab_about.*

/**
 * A simple [Fragment] subclass.
 */
class TabAboutFragment : Fragment() {

    companion object {

        lateinit var openSendReviewFragment: () -> Unit

        fun newInstance(fragmentCallback: () -> Unit): TabAboutFragment {
            openSendReviewFragment = fragmentCallback
            return TabAboutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log_in_about_btn.setOnClickListener {
            AboutDataRepository.openSendReviewFragment()
        }
        AboutDataRepository.getAboutText().observe(viewLifecycleOwner, Observer {
            tv_text_about1.text = Html.fromHtml(it[0])
            tv_text_about2.text = Html.fromHtml(it[1])
            tv_text_about3.text = Html.fromHtml(it[2])
        })
        AboutDataRepository.getAboutTitles().observe(viewLifecycleOwner, Observer {
            tv_title_about1.text = it[0]
            tv_title_about2.text = it[1]
            tv_title_about3.text = it[2]
        })

    }
}
