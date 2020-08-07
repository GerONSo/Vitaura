package com.example.vitaura.services

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vitaura.MainRepository

import com.example.vitaura.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_services.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ServicesFragment : Fragment() {

    lateinit var toolbar: Toolbar
    val viewModel = ServiceViewModel()
    lateinit var servicesAdapter: ServicesAdapter
    var serviceList: MutableList<Service?> = mutableListOf()
    var allServices: AllServices? = null
    var position = 0

    fun setData(newPosition: Int) {
        position = newPosition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        if (position < ServiceRepository.imageList.size) {
            iv_service_type_circle.setImageBitmap(ServiceRepository.imageList[position])
        }
        val text = ServiceRepository.serviceTypes[position]
        tv_service_type.text = text

        ServiceRepository.services.observe(viewLifecycleOwner, Observer {
            val type = it?.get(ServiceRepository.serviceTypesAlias[position])
            type?.let { type1 ->
                type1.sortBy { service ->
                    service?.weight
                }
                var list: MutableList<Service?> = mutableListOf()
                for (service in type1) {
//                    Log.d("service", service?.tid)
                    if(service != null && service.parentTid == "") {
                        list.add(service)
                    }
                }
                servicesAdapter = ServicesAdapter(list, text, position, type1)
                rv_services.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = servicesAdapter
                }
                servicesAdapter.notifyDataSetChanged()
            }
        })
    }

    fun updateUI() {
        toolbar = activity?.toolbar!!
        toolbar.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )
        toolbar.navigationIcon?.setColorFilter(
            resources.getColor(R.color.colorPrimary),
            PorterDuff.Mode.SRC_ATOP
        )
        val title = activity?.toolbar_title
        title?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
        title?.text = "Услуги"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }
}
