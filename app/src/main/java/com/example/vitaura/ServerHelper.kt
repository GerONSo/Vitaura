package com.example.vitaura

import android.util.Log
import com.example.vitaura.about.AboutDataParser
import com.example.vitaura.about.AboutDataRepository
import com.example.vitaura.doctors.DoctorsRepository
import com.example.vitaura.media.VideoAdapter
import com.example.vitaura.prices.Prices
import com.example.vitaura.prices.PricesDeserializer
import com.example.vitaura.prices.PricesRepository
import com.example.vitaura.reviews.Review
import com.example.vitaura.reviews.ReviewRepository
import com.example.vitaura.special.SpecialsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception


object ServerHelper {

    const val BASE_URL = "http://91.210.170.129:5000"
    const val BASE_URL2 = "https://vitaura-clinic.ru"

    fun getDoctors() {
        val service = makeApiService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getDoctors()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        var doctors = response.body()
                        DoctorsRepository.setDoctors(doctors!!)
                    } else {
                        Log.d("HTTP request", "Server didn't send response")
                    }
                } catch (e: HttpException) {
                    Log.d("HTTP request", "Server didn't send response")
                } catch (e: Throwable) {
                    Log.d("HTTP request", "Server didn't send response")
                }
            }
        }
    }

    fun getSpecials() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getSpecials()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val specials = response.body()
                        SpecialsRepository.setSpecials(specials!!)
                    } else {
                        Log.d("HTTP request", "Server didn't send response")
                    }
                } catch (e: Exception) {
                    Log.d("HTTP request", "Server didn't send response")
                }
            }
        }
    }

    fun getReviews() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getReviews()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        val reviews = response.body()
                        var reviewsList = arrayListOf<Review>()
                        for (i in 0 until reviews?.data?.size!!) {
                            reviewsList.add(
                                Review(
                                    reviews.data[i].attributes.title,
                                    reviews.data[i].attributes.text.value
                                )
                            )
                        }
                        ReviewRepository.setReviews(reviewsList)

                    } else {
                        Log.d("HTTP request", "Server didn't send response")
                    }
                } catch (e: Exception) {
                    Log.d("HTTP request", "Server didn't send response")
                }
            }
        }
    }

    fun getPrices() {
        val service = makeApiPriceService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPrices()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        PricesRepository.setPrices(response.body()!!)
                    } else {
                        Log.d("HTTP request", "Server didn't send response")
                    }

                } catch (e: Exception) {
                    Log.d("HTTP request", "Server didn't send response")
                }
            }
        }
    }

    fun getAboutData() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getAboutData()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {

                        AboutDataParser.parseAbout(
                            response.body()?.data?.get(0)?.attributes?.body?.text!!,
                            response.body()?.data?.get(0)?.attributes?.title!!
                        )

                        val licenseTitle1 = response.body()?.data?.get(3)?.attributes?.title
                        val licenseTitle2 = response.body()?.data?.get(8)?.attributes?.title
                        AboutDataRepository.setLicenseTitles(listOf(licenseTitle1, licenseTitle2))

                        val licenseText = AboutDataParser.parseLicenseEmail(response.body()?.data?.get(8)?.attributes?.body?.text)
                        AboutDataRepository.setLicenseText(listOf(licenseText))
                        Log.d("parse", licenseText)
                    } else {
                        Log.d("HTTP request", "Server didn't send response")
                    }

                } catch (e: Exception) {
                    Log.d("HTTP request", "Server didn't send response")
                }
            }
        }
    }

    fun getVideos() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getVideos()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {
                        var videoList = response.body()
                        videoList?.data = videoList?.data?.filter { it.attrs.link != null }!!
                        VideoAdapter.videoList = videoList
                    }
                    else {
                        Log.d("HTTP request", "Server didn't send response")
                    }
                } catch (e: Exception) {
                    Log.d("HTTP request", "Server didn't send response")
                }
            }
        }
    }

    fun getGallery() {
//        val service = makeApi2Service()
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = service.getGallery()
//            withContext(Dispatchers.Main) {
//                try {
//                    if(response.isSuccessful) {
//
//                    }
//                    else {
//                        Log.d("HTTP request", "Server didn't send response")
//                    }
//                } catch (e: Exception) {
//                    Log.d("HTTP request", "Server didn't send response")
//                }
//            }
//        }
    }

    private fun makeApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun makeApi2Service(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


    private fun makeApiPriceService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create(getGsonConverter()))
            .build()
            .create(ApiService::class.java)
    }

    private fun getGsonConverter(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                Prices::class.java,
                PricesDeserializer()
            )
            .create()
    }
}