package com.example.vitaura.send_review

import android.app.ActionBar
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import com.example.vitaura.about.TabAboutFragment
import com.example.vitaura.about.TabLicenseFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_send_review.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

/**
 * A simple [Fragment] subclass.
 */
class SendReviewFragment : Fragment() {

    lateinit var toolbar: Toolbar
    val REVIEW_TAG = "T1"
    val LOG_IN_TAG = "T2"
    val viewModel: SendReviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        send_review_tab_layout.apply {
            addTab(send_review_tab_layout.newTab().setText("Отзыв"))
            addTab(send_review_tab_layout.newTab().setText("Запись на прием"))
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (send_review_tab_layout.selectedTabPosition) {
                        0 -> {
                            viewModel.lastSavedFragment = SendReviewViewModel.SEND_REVIEW
                            changeSendReviewFragment(TabSendReviewFragment(), REVIEW_TAG)
                        }
                        1 -> {
                            viewModel.lastSavedFragment = SendReviewViewModel.LOGIN
                            changeSendReviewFragment(TabLoginFragment(), LOG_IN_TAG)
                        }
                    }
                }

            })
        }
        if(MainRepository.currentSendReviewTab == SendReviewViewModel.SEND_REVIEW) {
            val tab = send_review_tab_layout.getTabAt(0)
            tab?.select()
            changeSendReviewFragment(TabSendReviewFragment(), REVIEW_TAG)
        }
        else {
            val tab = send_review_tab_layout.getTabAt(1)
            tab?.select()
            changeSendReviewFragment(TabLoginFragment(), LOG_IN_TAG)
        }
    }

    private fun changeSendReviewFragment(newFragment: Fragment, tag: String) {
        childFragmentManager.beginTransaction()
            .replace(R.id.send_review_frame_container, newFragment, tag)
            .commit()
    }

    fun updateUI() {
        toolbar = activity?.toolbar!!
        toolbar.setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.colorAccent
            ))
        toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
        val title = activity?.toolbar_title
        title?.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.textColor
        ))
        title?.text = "Оставить отзыв"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }

}
