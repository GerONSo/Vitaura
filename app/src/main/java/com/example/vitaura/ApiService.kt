package com.example.vitaura

import com.example.vitaura.about.Pages
import com.example.vitaura.doctors.Doctor
import com.example.vitaura.doctors.DoctorsData
import com.example.vitaura.main.SliderData
import com.example.vitaura.media.gallery.ChangeFile
import com.example.vitaura.media.gallery.GalleryData
import com.example.vitaura.media.gallery.GalleryFile
import com.example.vitaura.media.gallery.GalleryFileData
import com.example.vitaura.media.video.VideoData
import com.example.vitaura.prices.PriceData
import com.example.vitaura.prices.Prices
import com.example.vitaura.reviews.Patients
import com.example.vitaura.send_review.ProblemData
import com.example.vitaura.services.NodeServiceData
import com.example.vitaura.services.Service
import com.example.vitaura.services.ServicesJSON
import com.example.vitaura.special.ActionsData
import com.example.vitaura.special.Special
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/get_doctors")
    suspend fun getDoctors(): Response<List<Doctor>>

    @GET("/rest/actions/service/all")
    suspend fun getSpecials(): Response<List<Special>>

    @GET("/jsonapi/node/patients")
    suspend fun getReviews(): Response<Patients>

    @GET("/blocks_for_page/special_links/price.html")
    suspend fun getPrices(): Response<Prices>

    @GET("/jsonapi/node/page")
    suspend fun getAboutData(): Response<Pages>

    @GET("/jsonapi/node/video")
    suspend fun getVideos(): Response<VideoData>

    @GET("/jsonapi/node/gallery")
    suspend fun getGallery(): Response<GalleryData>

    @GET("/jsonapi/file/file/{id}")
    suspend fun getFile(@Path("id") id: String): Response<GalleryFileData>

    @GET("/rest/do-i-posle")
    suspend fun getChangeGallery(): Response<List<ChangeFile>>

    @GET("/rest_mobile/services/{id}")
    suspend fun getServices(@Path("id") id: String): Response<MutableList<Service?>>

    @GET("/jsonapi/node/sliders")
    suspend fun getSlider(): Response<SliderData>

    @GET("/jsonapi/node/problem")
    suspend fun getProblems(): Response<ProblemData>

    @GET("/jsonapi/node/actions")
    suspend fun getActions(): Response<ActionsData>

    @GET("/jsonapi/node/doctors")
    suspend fun getNodeDoctors(): Response<DoctorsData>

    @GET("/jsonapi/node/price")
    suspend fun getNodePrices(): Response<PriceData>

    @GET("/jsonapi/taxonomy_term/services/{id}")
    suspend fun getService(@Path("id") id: String): Response<NodeServiceData>
}