package com.example.vitaura.send_review

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

import com.example.vitaura.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class SendReviewResultFragment : Fragment() {

    lateinit var toolbar: Toolbar

    companion object {

        lateinit var tag: String

        fun newInstance(newTag: String): SendReviewResultFragment {
            tag = newTag
            return SendReviewResultFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_review_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when(SendReviewResultFragment.tag) {
            SendReviewViewModel.SEND_REVIEW -> {
                updateUI("Отзыв")
            }
            SendReviewViewModel.LOGIN -> {
                updateUI("Запись на прием")
            }
        }
    }

    fun updateUI(newTitle: String) {
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
        title?.text = newTitle
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }
}
