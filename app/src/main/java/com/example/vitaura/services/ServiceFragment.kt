package com.example.vitaura.services

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
import com.example.vitaura.helpers.HtmlNormalizer
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import com.example.vitaura.helpers.GlideImageGetter
//import com.example.vitaura.helpers.ImageGetter
import com.example.vitaura.prices.PricesRepository
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_service.*

/**
 * A simple [Fragment] subclass.
 */
class ServiceFragment : Fragment() {

    lateinit var toolbar: Toolbar
    val viewModel = ServiceViewModel()
    var position: Int = 0
    var serviceTitle: String? = ""
    var serviceTypeTitle: String? = ""
    var parentPosition: Int = 0
    var serviceId: String = ""
    lateinit var imageGetter: GlideImageGetter

    fun setParams(newPosition: Int, newTitle: String?, newTypeTitle: String?, newParentPosition: Int, newServiceId: String) {
        position = newPosition
        serviceTitle = newTitle
        serviceTypeTitle = newTypeTitle
        parentPosition = newParentPosition
        serviceId = newServiceId
        viewModel.serviceId = newServiceId
//        viewModel.serviceTitle = serviceTitle
//        viewModel.serviceTypeTitle = serviceTypeTitle
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
        imageGetter = GlideImageGetter(tv_in_about_service)
        val service = ServiceRepository.services.value?.get(ServiceRepository.serviceTypesAlias[parentPosition])?.get(position)
        service_tab_layout.apply {
            addTab(service_tab_layout.newTab().setText("Эффективность"))
            addTab(service_tab_layout.newTab().setText("Преимущества"))
            addTab(service_tab_layout.newTab().setText("Противопоказания"))
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(service_tab_layout.selectedTabPosition) {
                        0 -> {
                            service?.let {
                                tv_in_about_service.text = Html.fromHtml(HtmlNormalizer.normalize(it.efficiency), imageGetter, null)
                            }
                        }

                        1 -> {
                            service?.let {
                                tv_in_about_service.text = Html.fromHtml(HtmlNormalizer.normalize(it.advantage), imageGetter, null)
//                                web_view.loadDataWithBaseURL(null, HtmlNormalizer.normalize(it.advantage), "text/html", "utf-8", null)
                            }
                        }

                        2 -> {
                            service?.let {
                                tv_in_about_service.text = Html.fromHtml(HtmlNormalizer.normalize(it.contraindication), imageGetter, null)
                            }
                        }
                    }
                }

            })
        }
        pb_service.visibility = View.VISIBLE
        rv_service_prices.visibility = View.GONE
        pb_service.indeterminateDrawable.setColorFilter(
            resources.getColor(R.color.colorPrimary),
            PorterDuff.Mode.SRC_IN);
        tv_in_service_type.text = ServiceRepository.services.value?.get(ServiceRepository.serviceTypesAlias[parentPosition])?.get(position)?.title
        tv_in_service_type_title.text = ServiceRepository.serviceTypes[parentPosition]
        if (parentPosition < ServiceRepository.imageList.size) {
            iv_in_service_type_circle.setImageBitmap(ServiceRepository.imageList[parentPosition])
        }
        val tab = service_tab_layout.getTabAt(0)
        tab?.select()
        service?.let {
            tv_in_about_service.text = Html.fromHtml(HtmlNormalizer.normalize(it.efficiency), imageGetter, null)
//            web_view.loadDataWithBaseURL(null, HtmlNormalizer.normalize(it.efficiency), "text/html", "utf-8", null)
        }
        if(!viewModel.mainRepository.serviceDoctorsMap.value.isNullOrEmpty()) {
            var list = viewModel.mainRepository.serviceDoctorsMap.value?.get(viewModel.serviceId)
            val newAdapter = ServiceDoctorsAdapter(list)
            rv_service_doctors.apply {
                adapter = newAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            newAdapter.notifyDataSetChanged()
        }
        viewModel.mainRepository.serviceDoctorsMap.observe(viewLifecycleOwner, Observer {
            val newAdapter = ServiceDoctorsAdapter(it[serviceId])
            rv_service_doctors.apply {
                adapter = newAdapter
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            newAdapter.notifyDataSetChanged()
        })
        viewModel.mainRepository.servicePricesMap.observe(viewLifecycleOwner, Observer {
            var nidList = arrayListOf<String>()
            var list = viewModel.mainRepository.servicePricesMap.value?.get(viewModel.serviceId)
            list?.let {
                for (priceElement in it) {
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
