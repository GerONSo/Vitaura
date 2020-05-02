package com.example.vitaura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.vitaura.about.AboutDataRepository
import com.example.vitaura.about.AboutFragment
import com.example.vitaura.doctors.DoctorFragment
import com.example.vitaura.doctors.DoctorsFragment
import com.example.vitaura.prices.PricesFragment
import com.example.vitaura.reviews.ReviewFragment
import com.example.vitaura.special.SpecialFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.IllegalStateException

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


    fun initData() = runBlocking {
        val job = GlobalScope.launch {
            ServerHelper.getAboutData()
            ServerHelper.getDoctors()
            ServerHelper.getPrices()
            ServerHelper.getReviews()
            ServerHelper.getSpecials()
        }
        job.join()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initData()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navigation_view.setNavigationItemSelectedListener(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initViews()
        initViewModel()

    }

    private fun initViews() {
        if(model.getFragmentStack().value?.size != 0) {
            for (fragmentTag in model.getFragmentStack().value!!) {
                val fragment = getFragmentByTag(fragmentTag)
//                changeFragmentWithoutAdding(fragment, fragmentTag)
            }
//            fragmentStack = model.getFragmentStack().value!!
        }
        else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, AboutFragment.newInstance {
                    changeFragment(SendReviewFragment(), SEND_REVIEW_FRAGMENT_TAG)
                }, ABOUT_FRAGMENT_TAG)
                .commit()
        }
        bottom_navigation_view.uncheckAllItems()
        AboutDataRepository.openSendReviewFragment = {
            changeFragment(SendReviewFragment(), SEND_REVIEW_FRAGMENT_TAG)
        }
        val bottomNavigationListener = BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.services -> {

                    true
                }

                R.id.doctors -> {
                    val doctorsFragment = DoctorsFragment.newInstance { position ->
                        val fragment = DoctorFragment.newInstance(position)
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
        bottom_navigation_view.setOnNavigationItemSelectedListener(bottomNavigationListener)
    }

    private fun initViewModel() {
        model.getFragmentStack().observe(this, Observer { fragmentStack ->
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
            R.id.about -> {
                val aboutFragment = AboutFragment.newInstance {
                    changeFragment(SendReviewFragment(), SEND_REVIEW_FRAGMENT_TAG)
                }
                changeFragment(aboutFragment, ABOUT_FRAGMENT_TAG)
            }

            R.id.special -> {
                val specialFragment = SpecialFragment()
                changeFragment(specialFragment, SPECIAL_FRAGMENT_TAG)
                toolbar_logo.visibility = View.INVISIBLE
                toolbar_title.visibility = View.VISIBLE
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return false
    }

    private fun changeFragment(newFragment: Fragment, tag: String) {
        if (model.getFragmentStack().value?.size == 0 ||
            model.getFragmentStack().value?.get(model.getFragmentStack().value!!.size - 1) != tag
        ) {
//            fragmentStack.add(tag)
            model.addToFragmentStack(tag)
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, newFragment, tag)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun changeFragmentWithoutAdding(newFragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, newFragment, tag)
            .addToBackStack(null)
            .commit()
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

    private fun getFragmentByTag(tag: String): Fragment =
        when (tag) {
            ABOUT_FRAGMENT_TAG -> AboutFragment.newInstance {
                changeFragment(SendReviewFragment(), SEND_REVIEW_FRAGMENT_TAG)
            }
            DOCTORS_FRAGMENT_TAG -> DoctorsFragment.newInstance { position ->
                val fragment = DoctorFragment.newInstance(position)
                changeFragment(fragment, DOCTOR_FRAGMENT_TAG)
            }
            SPECIAL_FRAGMENT_TAG -> SpecialFragment()
            REVIEW_FRAGMENT_TAG -> ReviewFragment()
            PRICES_FRAGMENT_TAG -> PricesFragment()
            DOCTOR_FRAGMENT_TAG -> DoctorFragment()
            SEND_REVIEW_FRAGMENT_TAG -> SendReviewFragment()
            else -> throw IllegalStateException("No fragment with such tag")
        }
}
