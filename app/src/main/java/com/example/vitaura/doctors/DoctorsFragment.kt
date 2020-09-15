package com.example.vitaura.doctors


import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vitaura.MainRepository
import com.example.vitaura.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_doctors.*

/**
 * A simple [Fragment] subclass.
 */
class DoctorsFragment : Fragment() {

    var doctorsAdapter: DoctorsAdapter? = null
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    val viewModel: DoctorViewModel by viewModels()

    companion object {

        lateinit var openDoctorFragment: (Int) -> Unit

        fun newInstance(openDoctorFragment: (Int) -> Unit): DoctorsFragment {
            Companion.openDoctorFragment = openDoctorFragment
            return DoctorsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_doctors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        MainRepository.sortNodeDoctors()
        for(i in MainRepository.nodeDoctors.value?.data!!) {
            Log.d("doctors", i.attrs.weight.toString())
        }
        doctorsAdapter = DoctorsAdapter(openDoctorFragment, viewModel)
        rv_doctors.apply {
            layoutManager = mLayoutManager
            adapter = doctorsAdapter
        }
        DoctorsRepository.getDoctors().observe(viewLifecycleOwner, Observer {
            doctorsAdapter!!.notifyDataSetChanged()
        })
        super.onViewCreated(view, savedInstanceState)
    }

    fun updateUI() {
        toolbar = activity?.toolbar!!
        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.colorAccent
        ))
        toolbar.navigationIcon?.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
        val title = activity?.toolbar_title
        title?.setTextColor(ContextCompat.getColor(requireContext(), R.color.textColor))
        title?.text = "Специалисты"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }
}
