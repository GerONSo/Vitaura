package com.example.vitaura

import com.example.vitaura.about.Pages
import com.example.vitaura.doctors.Doctor
import com.example.vitaura.media.gallery.GalleryData
import com.example.vitaura.media.gallery.GalleryFile
import com.example.vitaura.media.gallery.GalleryFileData
import com.example.vitaura.media.video.VideoData
import com.example.vitaura.prices.Prices
import com.example.vitaura.reviews.Patients
import com.example.vitaura.special.Special
import retrofit2.Response
import retrofit2.http.GET

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

    @GET("/jsonapi/file/file")
    suspend fun getFiles(): Response<GalleryFileData>
}