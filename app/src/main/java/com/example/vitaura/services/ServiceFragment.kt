package com.example.vitaura.services

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

import com.example.vitaura.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_service.*
import kotlinx.android.synthetic.main.fragment_services.*

/**
 * A simple [Fragment] subclass.
 */
class ServiceFragment : Fragment() {

    lateinit var toolbar: Toolbar

    companion object {

        var position: Int = 0
        var serviceTitle: String = ""
        var serviceTypeTitle: String = ""

        fun newInstance(newPosition: Int, newTitle: String, newTypeTitle: String): ServiceFragment {
            position = newPosition
            serviceTitle = newTitle
            serviceTypeTitle = newTypeTitle
            return ServiceFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        tv_in_service_type_title.text = serviceTitle
        tv_in_service_type.text = serviceTypeTitle
        if(ServicesFragment.position < ServiceRepository.imageList.size) {
            iv_in_service_type_circle.setImageBitmap(ServiceRepository.imageList[ServicesFragment.position])
        }
        var text = ServiceRepository.services.value?.get(position)?.data?.attrs?.body?.value
        text = text
            ?.replace("[block:webform=client-block-563]", "")
        if(text != null) {
            tv_in_about_service.text = Html.fromHtml(text)
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
        title?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
        title?.text = "Услуги"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }
}
