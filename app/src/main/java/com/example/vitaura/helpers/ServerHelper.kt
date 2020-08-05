package com.example.vitaura.helpers

import android.util.Log
import com.example.vitaura.ApiService
import com.example.vitaura.MainRepository
import com.example.vitaura.about.AboutDataParser
import com.example.vitaura.about.AboutDataRepository
import com.example.vitaura.doctors.Doctors
import com.example.vitaura.doctors.DoctorsRepository
import com.example.vitaura.media.MediaRepository
import com.example.vitaura.media.gallery.ChangeFile
import com.example.vitaura.media.video.VideoAdapter
import com.example.vitaura.prices.*
import com.example.vitaura.reviews.Review
import com.example.vitaura.reviews.ReviewRepository
import com.example.vitaura.services.NodeServiceData
import com.example.vitaura.services.ServiceRepository
import com.example.vitaura.special.SpecialsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object ServerHelper {

    const val BASE_URL = "http://91.210.170.129:5000"
    const val BASE_URL2 = "https://vitaura-clinic.ru"


    fun getDoctors() {
        val service = makeApiService()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getDoctors()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful && response.body() != null) {
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
                    if (response.isSuccessful && response.body() != null) {
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
                    if (response.isSuccessful && response.body() != null) {
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
        val service =
            makeApiPriceService()
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<Prices>? = null
            try {
                response = service.getPrices()
            } catch (e: MalformedJsonException) {
                Log.d("empty prices", "empty")
            }
            withContext(Dispatchers.Main) {
                try {
                    if (response != null && response.isSuccessful && response.body() != null) {
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
                    if (response.isSuccessful && response.body() != null) {

                        AboutDataParser.parseAbout(
                            response.body()?.data?.get(0)?.attributes?.body?.text!!,
                            response.body()?.data?.get(0)?.attributes?.title!!
                        )

                        val licenseTitle1 = response.body()?.data?.get(3)?.attributes?.title
                        val licenseTitle2 = response.body()?.data?.get(8)?.attributes?.title
                        AboutDataRepository.setLicenseTitles(listOf(licenseTitle1, licenseTitle2))

                        val licenseText = HtmlNormalizer.normalizeLicense(AboutDataParser.parseLicenseEmail(response.body()?.data?.get(8)?.attributes?.body?.text))
                        AboutDataRepository.setLicenseText(listOf(licenseText))

                        MediaRepository.parseFileData(response.body()?.data?.get(17)?.attributes?.body?.text!!)
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
                    if(response.isSuccessful && response.body() != null) {
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
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getGallery()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
                        MediaRepository.clinicGallery = response.body()?.data?.get(1)
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

    fun getFile(id: String) {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getFile(id)
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
                        MediaRepository.clinicFileData.value = MediaRepository.clinicFileData.value.also {
                            it?.add(response.body()?.data?.attrs?.uri?.url!!)
                        }
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

    fun getChangeGallery() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getChangeGallery()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
                        MediaRepository.changePathsList.value = response.body() as ArrayList<ChangeFile>
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

    fun getServiceTypes(id: String) {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getServices(id)
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {
                        response.body()?.let { response ->
                            ServiceRepository.services.value =
                                ServiceRepository.services.value.also {
                                    it?.set(id, response)
                                }
                        }
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

    fun getMainSlider() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getSlider()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
                        MainRepository.sliderIds.value = response.body()!!
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

    fun getSliderFile(id: String) {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getFile(id)
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
                        MainRepository.sliderMap.value = MainRepository.sliderMap.value.also {
                            it?.set(id, response.body()?.data?.attrs?.uri?.url!!)
                        }
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

    fun getProblems() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getProblems()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
                        MainRepository.sliderProblems.value = response.body()!!
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

    fun getActions() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getActions()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
                        SpecialsRepository.actions.value = response.body()!!
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

    fun getNodeDoctors() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getNodeDoctors()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
                        MainRepository.nodeDoctors.value = response.body()!!
                        MainRepository.sortNodeDoctors()
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

    fun getNodePrices() {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getNodePrices()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
                        MainRepository.nodePrices.value = response.body()!!
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

    fun getPrices2() {
        val service =
            makeApiPrice2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPrices()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful && response.body() != null) {
                        PricesRepository.servicePrices.value = response.body()
                    } else {
                        Log.d("HTTP request", "Server didn't send response")
                    }

                } catch (e: Exception) {
                    Log.d("HTTP request", "Server didn't send response")
                }
            }
        }
    }

    fun getService(id: String, doctor: Doctors) {
        val service = makeApi2Service2()
        try {
            CoroutineScope(Dispatchers.IO).launch {
                var response: Response<NodeServiceData>? = null
                try {
                    response = service.getService(id)
                } catch (e: java.lang.Exception) {
                    Log.d("malformed", id)
                }
                withContext(Dispatchers.Main) {
                    try {
                        if (response?.isSuccessful!!  && response.body() != null) {
//                        print(response.body())
                        MainRepository.serviceDoctorsMap.value = MainRepository.serviceDoctorsMap.value.also {
                            var list = it?.get(response.body()?.data?.attrs?.path?.alias) ?: arrayListOf()
                            it?.let { map ->
                                map[response.body()?.data?.attrs?.path?.alias!!] = list.also { arrayList ->
                                    arrayList.add(doctor)
                                }
                            }
                        }
                        } else {
                            Log.d("HTTP request", "Server didn't send response")
                        }
                    } catch (e: Exception) {
                        Log.d("HTTP request", "Server didn't send response")
                    }
                }
            }
        } catch (e: Exception) {
            print(e.stackTrace)
        }
    }

    fun getService(id: String, price: PriceElement) {
        val service = makeApi2Service()
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getService(id)
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful && response.body() != null) {
//                        print(response.body())
                        MainRepository.servicePricesMap.value = MainRepository.servicePricesMap.value.also {
                            var list = it?.get(response.body()?.data?.attrs?.path?.alias) ?: arrayListOf()
                            it?.let { map ->
                                map[response.body()?.data?.attrs?.path?.alias!!] = list.also { arrayList ->
                                    arrayList.add(price)
                                }
                            }
                        }
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


    private fun makeApiService(): ApiService {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun makeApi2Service(): ApiService {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun makeApi2Service2(): ApiService {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .client(client)
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun makeApiPriceService(): ApiService {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .client(client)
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

    private fun makeApiPrice2Service(): ApiService {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(getGsonConverter2()))
            .build()
            .create(ApiService::class.java)
    }

    private fun getGsonConverter2(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                Prices::class.java,
                PricesDeserializer2()
            )
            .create()
    }
}