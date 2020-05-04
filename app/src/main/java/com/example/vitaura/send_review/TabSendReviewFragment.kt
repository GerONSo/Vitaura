package com.example.vitaura.send_review

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

import com.example.vitaura.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tab_send_review.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

/**
 * A simple [Fragment] subclass.
 */
class TabSendReviewFragment : Fragment() {

    val sendReviewViewModel: SendReviewViewModel by viewModels()
    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_send_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        btn_send_review.setOnClickListener {
            if(et_name.text.toString() != "" && et_review.text.toString() != "") {
                SendReviewRepository.openSendReviewResult(SendReviewViewModel.SEND_REVIEW)
            }
        }
        et_name.setText(sendReviewViewModel.getName().value!!)
        et_phone.setText(sendReviewViewModel.getNumber().value!!)
        et_review.setText(sendReviewViewModel.getReview().value!!)
        KeyboardVisibilityEvent.setEventListener(activity) {
            if(it) {
                send_review_logo_layout?.visibility = View.GONE
                view_to_move?.layoutParams = LinearLayout.LayoutParams(1, 0, 2f)
            }
            else {
                send_review_logo_layout?.visibility = View.VISIBLE
                view_to_move?.layoutParams = LinearLayout.LayoutParams(1, 0, 0f)
            }
        }
    }

    override fun onDestroyView() {
        sendReviewViewModel.setName(et_name.text.toString())
        sendReviewViewModel.setNumber(et_phone.text.toString())
        sendReviewViewModel.setReview(et_review.text.toString())
        super.onDestroyView()
    }

    fun updateUI() {
        toolbar = activity?.toolbar!!
        toolbar.setBackgroundColor(
            ContextCompat.getColor(requireContext(),
                R.color.colorAccent
            ))
        toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
        val title = activity?.toolbar_title
        title?.setTextColor(
            ContextCompat.getColor(requireContext(),
                R.color.textColor
            ))
        title?.text = "Оставить отзыв"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }
}
