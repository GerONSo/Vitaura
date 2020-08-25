package com.example.vitaura

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.vitaura.about.AboutDataRepository
import com.example.vitaura.about.AboutFragment
import com.example.vitaura.contacts.ContactsFragment
import com.example.vitaura.doctors.DoctorFragment
import com.example.vitaura.doctors.DoctorsFragment
import com.example.vitaura.helpers.ServerHelper
import com.example.vitaura.main.MainFragment
import com.example.vitaura.media.MediaFragment
import com.example.vitaura.media.MediaRepository
import com.example.vitaura.media.gallery.GalleryFragment
import com.example.vitaura.media.video.YouTubePlayerFragment
import com.example.vitaura.prices.PricesFragment
import com.example.vitaura.reviews.ReviewFragment
import com.example.vitaura.send_review.SendReviewFragment
import com.example.vitaura.send_review.SendReviewRepository
import com.example.vitaura.send_review.SendReviewResultFragment
import com.example.vitaura.send_review.SendReviewViewModel
import com.example.vitaura.services.*
import com.example.vitaura.special.ActionFragment
import com.example.vitaura.special.SpecialFragment
import com.example.vitaura.special.SpecialsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //    var fragmentStack = ArrayList<String>()
    val model: MainViewModel by viewModels()
    val ABOUT_FRAGMENT_TAG = "F0"
    val DOCTORS_FRAGMENT_TAG = "F1"
    val SPECIAL_FRAGMENT_TAG = "F2"
    val REVIEW_FRAGMENT_TAG = "F3"
    val PRICES_FRAGMENT_TAG = "F4"
    val DOCTOR_FRAGMENT_TAG = "F5"
    val SEND_REVIEW_FRAGMENT_TAG = "F6"
    val SEND_REVIEW_RESULT_FRAGMENT_TAG = "F7"
    val MEDIA_FRAGMENT_TAG = "F8"
    val GALLERY_FRAGMENT_TAG = "F9"
    val YOU_TUBE_PLAYER_FRAGMENT_TAG = "F10"
    val SERVICE_TYPES_FRAGMENT = "F11"
    val SERVICES_FRAGMENT = "F12"
    val SERVICE_FRAGMENT = "F13"
    val MAIN_FRAGMENT = "F14"
    val ACTION_FRAGMENT = "F15"
    val CONTACTS_FRAGMENT_TAG = "F16"
    val SERVICE_LIST_FRAGMENT = "F17"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setSupportActionBar(toolbar)
        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigation_view.setNavigationItemSelectedListener(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initViewModel()

    }

    private fun initViews() {
        if (model.getFragmentStack().value?.size == 0) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, MainFragment(), MAIN_FRAGMENT)
                    .commit()
        }
        bottom_navigation_view.uncheckAllItems()
        AboutDataRepository.openSendReviewFragment = {
            changeFragment(SendReviewFragment(), SEND_REVIEW_FRAGMENT_TAG)
        }
        MainRepository.openSendReviewFragment = {
            changeFragment(SendReviewFragment(), SEND_REVIEW_FRAGMENT_TAG)
        }
        MediaRepository.openGalleryFragment = {
            changeFragment(GalleryFragment.newInstance(it), GALLERY_FRAGMENT_TAG)
        }
        MediaRepository.openYouTubePlayerFragment = {
            changeFragment(YouTubePlayerFragment.newInstance(it), YOU_TUBE_PLAYER_FRAGMENT_TAG)
        }
        ServiceRepository.openServicesFragment = { pos ->
            changeFragment(ServicesFragment().also { it.setData(pos) }, SERVICES_FRAGMENT)
        }
        ServiceRepository.openServiceListFragment = { list ->
            changeFragment(ServiceListFragment().also { it.list = list }, SERVICE_LIST_FRAGMENT)
        }
        ServiceRepository.openServiceFragment = { pos, t1, t2, parentPosition, serviceId, service ->
            changeFragment(ServiceFragment().also {
                it.setParams(
                        pos,
                        t1,
                        t2,
                        parentPosition,
                        serviceId,
                        service
                )
            }, SERVICE_FRAGMENT)
        }
        SendReviewRepository.openSendReviewResult = { tag ->
            changeFragment(
                    SendReviewResultFragment.newInstance(tag),
                    SEND_REVIEW_RESULT_FRAGMENT_TAG
            )
        }
        SpecialsRepository.openActionFragment = { title, body, position ->
            changeFragment(
                    ActionFragment().also { it.setParams(title, body, position) },
                    ACTION_FRAGMENT
            )
        }
        MainRepository.openDoctorFragment = { position ->
            val fragment = DoctorFragment().also {
                it.setData(position)
            }
            changeFragment(fragment, DOCTOR_FRAGMENT_TAG)
        }
        ServiceRepository.imageList = listOf(
                BitmapFactory.decodeResource(resources, R.drawable.face),
                BitmapFactory.decodeResource(resources, R.drawable.body),
                BitmapFactory.decodeResource(resources, R.drawable.hair),
                BitmapFactory.decodeResource(resources, R.drawable.intim),
                BitmapFactory.decodeResource(resources, R.drawable.diagnostics)
        )
        val bottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.services -> {
                    changeFragment(ServicesTypesFragment(), SERVICE_TYPES_FRAGMENT)
                    true
                }

                R.id.doctors -> {
                    val doctorsFragment = DoctorsFragment.newInstance { position ->
                        val fragment =
                                DoctorFragment().also { fragment -> fragment.setData(position) }
                        changeFragment(fragment, DOCTOR_FRAGMENT_TAG)
                    }
                    changeFragment(doctorsFragment, DOCTORS_FRAGMENT_TAG)
                    toolbar_logo.visibility = View.INVISIBLE
                    toolbar_title.visibility = View.VISIBLE
                    true
                }

                R.id.reviews -> {
                    val reviewFragment = ReviewFragment()
                    changeFragment(reviewFragment, REVIEW_FRAGMENT_TAG)
                    true
                }

                R.id.prices -> {
                    val pricesFragment = PricesFragment()
                    changeFragment(pricesFragment, PRICES_FRAGMENT_TAG)
                    true
                }

                else -> false
            }
        }
        sign_up_btn.setOnClickListener {
            MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
            changeFragment(SendReviewFragment(), SEND_REVIEW_FRAGMENT_TAG)
            drawer.closeDrawer(GravityCompat.START)
        }
        image_btn.setOnClickListener {
            MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
            changeFragment(SendReviewFragment(), SEND_REVIEW_FRAGMENT_TAG)
            drawer.closeDrawer(GravityCompat.START)
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener(bottomNavigationListener)
        up_toolbar_layout.setOnClickListener {
            changeFragment(ContactsFragment(), CONTACTS_FRAGMENT_TAG)
        }
    }

    private fun initViewModel() {
        var resultList: MutableList<String?> = mutableListOf()
        var problemList: MutableList<String?> = mutableListOf()
        MainRepository.sliderIds.observe(this, Observer {
            resultList = Collections.nCopies(it.data.size, "").toMutableList()
            for (slider in it.data) {
                ServerHelper.getSliderFile(slider.relationships.photo.data.id)
            }
        })
        MainRepository.sliderMap.observe(this, Observer {
            for (i in 0 until resultList.size) {
                resultList[i] =
                        it[MainRepository.sliderIds.value?.data?.get(i)?.relationships?.photo?.data?.id]
            }
            MainRepository.sliderImages.value = resultList.toList()
        })
        MainRepository.nidList.observe(this, Observer {
            ServerHelper.getPrices2()
        })
        MainRepository.nodeDoctors.observe(this, Observer {
            for (doctors in it.data) {
                for (service in doctors.relationships.services.data) {
                    ServerHelper.getService(service.id, doctors, doctors.relationships.services.data.size)
                }
            }
        })
        MainRepository.nodePrices.observe(this, Observer {
            for (priceElement in it.data) {
                for (priceService in priceElement.relationships.services.data) {
                    ServerHelper.getService(priceService.id, priceElement)
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (!drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.openDrawer(GravityCompat.START)
                } else {
                    drawer.closeDrawers()
                }
                true
            }
            R.id.back -> {
                popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun popBackStack() {
        if (model.getFragmentStack().value?.size!! > 0) {
            model.removeLast()
//            fragmentStack.removeAt(fragmentStack.size - 1)
        }
        supportFragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            popBackStack()
//            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        bottom_navigation_view.uncheckAllItems()
        when (item.itemId) {
            R.id.main -> {
                changeFragment(MainFragment(), MAIN_FRAGMENT)
            }
            R.id.about -> {
                val aboutFragment = AboutFragment()
                changeFragment(aboutFragment, ABOUT_FRAGMENT_TAG)
            }

            R.id.special -> {
                val specialFragment = SpecialFragment()
                changeFragment(specialFragment, SPECIAL_FRAGMENT_TAG)
            }
            R.id.media -> {
                changeFragment(MediaFragment(), MEDIA_FRAGMENT_TAG)
            }
            R.id.services -> {
                changeFragment(ServicesTypesFragment(), SERVICE_TYPES_FRAGMENT)
            }

            R.id.doctors -> {
                val doctorsFragment = DoctorsFragment.newInstance { position ->
                    val fragment = DoctorFragment().also { fragment -> fragment.setData(position) }
                    changeFragment(fragment, DOCTOR_FRAGMENT_TAG)
                }
                changeFragment(doctorsFragment, DOCTORS_FRAGMENT_TAG)
            }

            R.id.reviews -> {
                val reviewFragment = ReviewFragment()
                changeFragment(reviewFragment, REVIEW_FRAGMENT_TAG)
            }

            R.id.contacts -> {
                changeFragment(ContactsFragment(), CONTACTS_FRAGMENT_TAG)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return false
    }

    private fun changeFragment(newFragment: Fragment, tag: String) {
        if (model.getFragmentStack().value?.size == 0 ||
                model.getFragmentStack().value?.get(model.getFragmentStack().value!!.size - 1) != tag ||
                model.getFragmentStack().value?.get(model.getFragmentStack().value!!.size - 1) == ACTION_FRAGMENT ||
                model.getFragmentStack().value?.get(model.getFragmentStack().value!!.size - 1) == DOCTOR_FRAGMENT_TAG
        ) {
//            fragmentStack.add(tag)
            if (!supportFragmentManager.isDestroyed) {
                model.addToFragmentStack(tag)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, newFragment, tag)
                        .addToBackStack(null)
                        .commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun BottomNavigationView.uncheckAllItems() {
        menu.setGroupCheckable(0, true, false)
        for (i in 0 until menu.size()) {
            menu.getItem(i).isChecked = false
        }
        menu.setGroupCheckable(0, true, true)
    }

    fun onFlowerClicked(view: View) {
        MainRepository.currentSendReviewTab = SendReviewViewModel.LOGIN
        changeFragment(SendReviewFragment(), SEND_REVIEW_FRAGMENT_TAG)
    }
}
