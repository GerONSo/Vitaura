package com.example.vitaura.services

import android.database.DataSetObserver
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import com.example.vitaura.doctors.DoctorAttributes
import com.example.vitaura.prices.PricesRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * A simple [Fragment] subclass.
 */
class ServiceFragment : Fragment() {

    lateinit var toolbar: Toolbar
    var position: Int = 0
    var serviceTitle: String = ""
    var serviceTypeTitle: String = ""

    fun setParams(newPosition: Int, newTitle: String, newTypeTitle: String) {
        position = newPosition
        serviceTitle = newTitle
        serviceTypeTitle = newTypeTitle

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
        pb_service.visibility = View.VISIBLE
        rv_service_prices.visibility = View.GONE
        pb_service.indeterminateDrawable.setColorFilter(
            resources.getColor(R.color.colorPrimary),
            PorterDuff.Mode.SRC_IN);
        tv_in_service_type_title.text = serviceTitle
        tv_in_service_type.text = serviceTypeTitle
        if (ServicesFragment.position < ServiceRepository.imageList.size) {
            iv_in_service_type_circle.setImageBitmap(ServiceRepository.imageList[ServicesFragment.position])
        }
        var text = ServiceRepository.services.value?.get(position)?.data?.attrs?.body?.value
        text = text
            ?.replace("[block:webform=client-block-563]", "")
        if (text != null) {
            tv_in_about_service.text = Html.fromHtml(text)
        }
        MainRepository.nodeDoctors.observe(viewLifecycleOwner, Observer {
            var serviceDoctorsList: ArrayList<DoctorAttributes?> = arrayListOf()
            for(i in it.data) {
                val doctors = i.relationships.services.data
                var flag = false
                for (doctorService in doctors) {
                    if (doctorService.id == ServiceRepository.services.value?.get(position)?.data?.id) {
                        flag = true
                    }
                }
                if (flag) {
                    serviceDoctorsList.add(i.attrs)
                }
            }
            rv_service_doctors.apply {
                adapter = ServiceDoctorsAdapter(serviceDoctorsList)
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        })
        MainRepository.nodePrices.observe(viewLifecycleOwner, Observer {
            var nidList = arrayListOf<String>()
            for(priceElement in it.data) {
                var flag = false
                for(priceService in priceElement.relationships.services.data) {
                    if(priceService.id == ServiceRepository.services.value?.get(position)?.data?.id) {
                        flag = true
                    }
                }
                if(flag) {
                    nidList.add(priceElement.attrs.nid)
                }
            }
            MainRepository.nidList.value = nidList
        })

        PricesRepository.servicePrices.observe(viewLifecycleOwner, Observer {
            val servicePricesAdapter = ServicePriceSetAdapter()
            rv_service_prices.apply {
                adapter = servicePricesAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            servicePricesAdapter.notifyDataSetChanged()
            pb_service.visibility = View.GONE
            rv_service_prices.visibility = View.VISIBLE
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
