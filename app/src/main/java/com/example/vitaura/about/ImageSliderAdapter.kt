package com.example.vitaura.about

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import coil.Coil
import coil.request.GetRequest
import coil.request.LoadRequest
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import com.example.vitaura.doctors.DoctorsRepository
import com.example.vitaura.main.SliderData
import com.example.vitaura.services.ServiceRepository
import com.example.vitaura.services.ServiceViewModel
import com.example.vitaura.services.ServicesAdapter
import com.example.vitaura.special.SpecialsRepository
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_services.*
import kotlinx.android.synthetic.main.image_slider_layout_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ImageSliderAdapter(
    var imageList: List<String?>,
    var sliderData: SliderData? = null
) : SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH>() {

    private val IMAGE_URL = "https://vitaura-clinic.ru"
    lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        context = parent?.context!!
        return SliderAdapterVH(
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        )
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
//        Picasso.get()
//            .load(IMAGE_URL + imageList[position])
//            .into(viewHolder?.imageViewBackground)
        GlobalScope.launch {
            val imageLoader = Coil.imageLoader(context)
            val path = IMAGE_URL + imageList[position]
            val request = GetRequest.Builder(context)
                .data(path)
                .build()
            val drawable = imageLoader.execute(request).drawable
            withContext(Dispatchers.Main) {
                viewHolder?.imageViewBackground?.setImageDrawable(drawable)
            }

        }
        if (sliderData != null) {
            val title = sliderData?.data?.get(position)?.attrs?.title
            val body = sliderData?.data?.get(position)?.attrs?.body?.value
            viewHolder?.titleTextView?.text = Html.fromHtml(title)
            viewHolder?.bodyTextView?.text = Html.fromHtml(body)
            var pos = 0
            SpecialsRepository.getSpecials().value?.let {
                for (i in it.indices) {
                    val special = it[i]
                    if (special?.id == sliderData?.data?.get(position)?.attrs?.link) {
                        pos = i
                    }
                }
            }
            viewHolder?.view?.setOnClickListener {
                val alias = sliderData?.data?.get(position)?.attrs?.link
                SpecialsRepository.actions.value?.data?.let {
                    for (i in it.indices) {
                        val action = it[i]
                        if (action.attrs.path.alias == alias) {
                            val title = action.attrs.title
                            val body = action.attrs.body.value
                            SpecialsRepository.openActionFragment(title, body, i)
                        }
                    }
                }
                for (i in ServiceRepository.serviceTypesAlias.indices) {
                    val serviceAlias = ServiceRepository.serviceTypesAlias[i]
                    for (service in ServiceRepository.services.value?.get(serviceAlias)!!) {
                        service?.id?.let { id ->
                            if(service.id == sliderData?.data?.get(position)?.attrs?.link) {
                                ServiceRepository.openServiceFragment(0, "", "", i, id, service)
                            }
                        }
                    }
                }
            }
        }
    }

    inner class SliderAdapterVH(var view: View) : SliderViewAdapter.ViewHolder(view) {
        val imageViewBackground: ImageView = view.iv_auto_image_slider
        val titleTextView = view.tv_slider_title
        val bodyTextView = view.tv_slider_body
    }

}