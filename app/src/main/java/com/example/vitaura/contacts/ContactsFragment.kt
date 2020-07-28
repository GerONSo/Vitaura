package com.example.vitaura.contacts

import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.vitaura.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_contacts.*


/**
 * A simple [Fragment] subclass.
 */
class ContactsFragment : Fragment() {

    lateinit var toolbar: Toolbar
    val facebookUrl = "https://www.facebook.com/vitaura.clinic/?fref=ts"
    val instUrl = "https://www.instagram.com/vitaura.clinic/"
    val youTubeUrl = "https://www.youtube.com/channel/UCsoIyOrUiy-OTOImJWzpTNw"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        val map1Text = "<iframe src=\"https://yandex.ru/map-widget/v1/?um=constructor%3A9009972b4270fe453b40200c06e13c147fc9b215a7fac2fe109a095def6cd4e2&amp;source=constructor\" width=\"100%\" height=\"593\" frameborder=\"0\"></iframe>"
        map1.settings.javaScriptEnabled = true
        map1.loadData(map1Text, "text/html", null)
        val map2Text = "<iframe src=\"https://yandex.ru/map-widget/v1/?um=constructor%3A5b0a14d7e64a5ce9cdb72ab3b312d3049908d5b491e61142894b2d9dc35e74a1&amp;source=constructor\" width=\"100%\" height=\"593\" frameborder=\"0\"></iframe>"
        map2.settings.javaScriptEnabled = true
        map2.loadData(map2Text, "text/html", null)
        tv_map1.text = Html.fromHtml("<b>м. Белорусская:</b><br/>выход в сторону Белорусского вокзала, далее по 2-й Бресткой улице, затем по Большой Грузниской ул. С Большой Грузинской улицы поворачиваете в Большой Тишинский переулок.")
        tv_map2.text = Html.fromHtml("<b>м. Баррикадная:</b><br/>" +
                "выход в сторону Большой Грузинской улицы. Двигаетесь по ней прямо, далее поворот налевов Большой Тишинский переулок. Ориентир — Сбербанк на углу Большого Тишинского переулка.")
        soc_icon1_contacts.setOnClickListener {
            startActivityByUrl(facebookUrl)
        }
        soc_icon2_contacts.setOnClickListener {
            startActivityByUrl(instUrl)
        }
        soc_icon3_contacts.setOnClickListener {
            startActivityByUrl(youTubeUrl)
        }

    }

    fun startActivityByUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
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
        title?.text = "Контакты"
        val toolbarLogo = activity?.toolbar_logo
        toolbarLogo?.visibility = View.INVISIBLE
        val toolbarTitle = activity?.toolbar_title
        toolbarTitle?.visibility = View.VISIBLE
    }

    fun getId(view: View): String? {
        return if (view.id == View.NO_ID) "no-id"
        else view.resources
            .getResourceName(view.id)
    }
}
