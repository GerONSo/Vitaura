package com.example.vitaura.send_review

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.vitaura.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tab_login.*
import kotlinx.android.synthetic.main.review_card.view.*

/**
 * A simple [Fragment] subclass.
 */
class TabLoginFragment : Fragment() {

    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        btn_login.setOnClickListener {
            if(et_comment.text.toString() != "" && et_name_login.text.toString() != "") {
                val name = et_name_login.text?.toString()
                val phone = et_phone_login.text?.toString()
                val email = et_email.text?.toString()
                val date = et_date.text?.toString()
                val comment = et_comment.text?.toString()
                SendReviewRepository.sendEmail(
                    "Новая запись на прием в приложении Vitaura Clinic",
                    SendReviewRepository.getLoginBody(name, phone, email, date, comment))
                SendReviewRepository.openSendReviewResult(SendReviewViewModel.LOGIN)
            }
            else{
                Toast.LENGTH_LONG
                Toast.makeText(context,"Пожалуйста заполните все поля ", Toast.LENGTH_LONG ).show()
            }
        }
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
        title?.text = "Запись на прием"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }

}
