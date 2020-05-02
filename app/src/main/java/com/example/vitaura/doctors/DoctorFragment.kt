package com.example.vitaura.doctors


import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.example.vitaura.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_doctor.*

/**
 * A simple [Fragment] subclass.
 */
class DoctorFragment : Fragment() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var doctor: Doctor? = null

    companion object {

        var position = -1

        fun newInstance(): DoctorFragment {
            return DoctorFragment()
        }

        fun newInstance(position: Int): DoctorFragment {
            this.position = position
            return DoctorFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        doctor = DoctorsRepository.getDoctors()
            .value?.get(position)
        description_tv.text = Html.fromHtml(doctor?.description)
        val portraitImageView: ImageView = toolbar_top.findViewById(R.id.portrait_iv_profile)
        val nameTextView: TextView = toolbar_top.findViewById(R.id.name_tv_profile)
        val specTextView: TextView = toolbar_top.findViewById(R.id.spec_tv_profile)

        Picasso.get()
            .load("https://vitaura-clinic.ru/sites/default/files/${doctor?.photoName}")
            .into(portraitImageView)
        nameTextView.text = doctor?.name
        specTextView.text = doctor?.spec
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        toolbar.menu.clear()
        inflater.inflate(R.menu.toolbar_menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun updateUI() {
        toolbar = activity?.toolbar!!
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.toolbarColorBlue
        ))
        toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
        toolbar.elevation = 0f
        val title = activity?.toolbar_title
        title?.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.colorAccent
        ))
    }
}
