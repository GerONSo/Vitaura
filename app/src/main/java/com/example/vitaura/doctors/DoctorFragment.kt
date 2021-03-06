package com.example.vitaura.doctors


import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.vitaura.helpers.HtmlNormalizer
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import com.example.vitaura.send_review.SendReviewViewModel
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_doctor.*

/**
 * A simple [Fragment] subclass.
 */
class DoctorFragment : Fragment() {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var doctor: Doctor? = null
    var position = 0
    val viewModel: DoctorViewModel by viewModels()

    fun setData(newPosition: Int) {
        position = newPosition
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
        val doctor = MainRepository.nodeDoctors.value?.data?.get(position)?.attrs
        doctor_tab_layout.apply {
            addTab(doctor_tab_layout.newTab().setText("Информация"))
            addTab(doctor_tab_layout.newTab().setText("Специализация"))
            addTab(doctor_tab_layout.newTab().setText("Образование"))
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {}

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab?) {

                    when (doctor_tab_layout.selectedTabPosition) {
                        0 -> {
                            doctor?.information?.value?.let {
                                val info = HtmlNormalizer.normalize(it)
                                description_tv.text = Html.fromHtml(info)
                            }
                        }

                        1 -> {
                            doctor?.specialization?.value?.let {
                                val spec = HtmlNormalizer.normalize(it)
                                description_tv.text = Html.fromHtml(spec)
                            }
                        }

                        2 -> {
                            doctor?.education?.value?.let {
                                val educ = HtmlNormalizer.normalize(it)
                                description_tv.text = Html.fromHtml(educ)
                            }
                        }
                    }
                }

            })
        }
        val tab = doctor_tab_layout.getTabAt(0)
        tab?.select()
        doctor?.information?.value?.let {
            var info = HtmlNormalizer.normalize(it)
            description_tv.text = Html.fromHtml(info)
        }
//        val doctor2 = DoctorsRepository.getDoctors()
//            .value?.get(position)
        var doctor2: Doctor? = null
        var pos: Int = 0
        for (i in DoctorsRepository.getDoctors().value?.indices!!) {
            val d = DoctorsRepository.getDoctors().value?.get(i)
            if (d?.name?.replace("\n", " ")?.substring(0, 2) == doctor?.title?.substring(0, 2)) {
                doctor2 = d
                pos = i
                break
            }
        }

        val portraitImageView: ImageView = toolbar_top.findViewById(R.id.portrait_iv_profile)
        val nameTextView: TextView = toolbar_top.findViewById(R.id.name_tv_profile)
        val specTextView: TextView = toolbar_top.findViewById(R.id.spec_tv_profile)

        Picasso.get()
            .load("https://vitaura-clinic.ru/sites/default/files/${doctor2?.photoName}")
            .into(portraitImageView)
        nameTextView.text = doctor2?.name
        specTextView.text = doctor2?.spec
        cost2_tv.text = doctor?.consultationPrice

        val otherDoctorView1 = view.findViewById<View>(R.id.other_doctor_card_1)
        val otherDoctorView2 = view.findViewById<View>(R.id.other_doctor_card_2)
        Log.d("pos", pos.toString())
        setOtherDoctorsData(
            otherDoctorView1,
            (pos + 1) % DoctorsRepository.getDoctors().value?.size!!
        )
        setOtherDoctorsData(
            otherDoctorView2,
            (pos + 2) % DoctorsRepository.getDoctors().value?.size!!
        )
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        toolbar.menu.clear()
        inflater.inflate(R.menu.toolbar_menu_profile, menu)
        log_in_btn.setOnClickListener {
            MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
            MainRepository.openSendReviewFragment()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun updateUI() {
        toolbar = activity?.toolbar!!
        toolbar.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.toolbarColorBlue
            )
        )
        toolbar.navigationIcon?.setColorFilter(
            resources.getColor(R.color.colorAccent),
            PorterDuff.Mode.SRC_ATOP
        )
        toolbar.elevation = 0f
        val title = activity?.toolbar_title
        title?.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.colorAccent
            )
        )
        title?.text = "Специалисты"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }

    fun setOtherDoctorsData(holder: View, position: Int) {
        val nameTextView = holder.findViewById<TextView>(R.id.name_tv)
        val specTextView = holder.findViewById<TextView>(R.id.spec_tv)
        val loginButton = holder.findViewById<Button>(R.id.log_in_btn)
        val portraitImageView = holder.findViewById<ImageView>(R.id.portrait_iv)
        val descriptionTextView = holder.findViewById<TextView>(R.id.description)
        nameTextView.text = DoctorsRepository.getDoctors().value?.get(position)?.name
        specTextView.text = DoctorsRepository.getDoctors().value?.get(position)?.spec
        loginButton.setOnClickListener {
            MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
            MainRepository.openSendReviewFragment()
        }
        Picasso.get()
            .load(
                "https://vitaura-clinic.ru/sites/default/files/${DoctorsRepository.getDoctors().value?.get(
                    position
                )?.photoName}"
            )
            .into(portraitImageView)
        val miniDescription = DoctorsRepository.getDoctors().value?.get(position)?.miniDescription
        if (miniDescription?.length!! > 0 && miniDescription[0].isLetter()) {
            descriptionTextView.text = Html.fromHtml(miniDescription)
            descriptionTextView.visibility = View.VISIBLE
        } else {
            descriptionTextView.visibility = View.GONE
        }
        holder.setOnClickListener {
            DoctorsRepository.position = position
            for (pos in MainRepository.nodeDoctors.value?.data?.indices!!) {
                var i = MainRepository.nodeDoctors.value?.data?.get(pos)
                if (i?.attrs?.title == DoctorsRepository.getDoctors().value?.get(position)?.name?.replace("\n", " ")) {
                    MainRepository.openDoctorFragment(pos)
                }
            }
        }
    }
}
