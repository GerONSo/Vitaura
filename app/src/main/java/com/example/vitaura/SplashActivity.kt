package com.example.vitaura

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.example.vitaura.helpers.ServerHelper
import com.example.vitaura.services.ServiceRepository
import com.google.gson.stream.MalformedJsonException
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initData()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        splash_spinner.indeterminateDrawable.setColorFilter(getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
        val welcomeThread: Thread = object : Thread() {
            override fun run() {
                try {
                    super.run()
                    sleep(4000) //Delay of 10 seconds
                } catch (e: Exception) {
                } finally {
                    val i = Intent(
                        applicationContext,
                        MainActivity::class.java
                    )
                    startActivity(i)
                    finish()
                }
            }
        }
        welcomeThread.start()
    }

    fun initData() = runBlocking {
        val job = GlobalScope.launch {
            ServerHelper.apply {
                getMainSlider()
                getProblems()
                getActions()
                getAboutData()
                getDoctors()
                getPrices()
                getReviews()
                getSpecials()
                getVideos()
                getGallery()
                getChangeGallery()
                for(type in ServiceRepository.serviceTypesAlias) {
                    getServiceTypes(type)
                }
                getNodeDoctors()
                getNodePrices()
            }
        }
        job.join()
    }
}
