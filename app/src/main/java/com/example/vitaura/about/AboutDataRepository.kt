package com.example.vitaura.about

import androidx.lifecycle.MutableLiveData
import com.example.vitaura.doctors.Doctor

object AboutDataRepository {
    private var aboutImages: MutableLiveData<List<String?>> = MutableLiveData()
    private var aboutText: MutableLiveData<List<String?>> = MutableLiveData()
    private var aboutTitles: MutableLiveData<List<String?>> = MutableLiveData()

    private var licenseTitles: MutableLiveData<List<String?>> = MutableLiveData()
    private var licenseText: MutableLiveData<List<String?>> = MutableLiveData()
    lateinit var openSendReviewFragment: () -> Unit

    fun getAboutImages(): MutableLiveData<List<String?>> {
        return aboutImages
    }

    fun setAboutImages(newImages: List<String?>) {
        aboutImages.value = newImages
    }

    fun getAboutText(): MutableLiveData<List<String?>> {
        return aboutText
    }

    fun setAboutText(newText: List<String?>) {
        aboutText.value = newText
    }

    fun getAboutTitles(): MutableLiveData<List<String?>> {
        return aboutTitles
    }

    fun setAboutTitles(newAboutTitles: List<String?>) {
        aboutTitles.value = newAboutTitles
    }

    fun getLicenseTitles(): MutableLiveData<List<String?>> {
        return licenseTitles
    }

    fun setLicenseTitles(newLicenseTitles: List<String?>) {
        licenseTitles.value = newLicenseTitles
    }

    fun getLicenseText(): MutableLiveData<List<String?>> {
        return licenseText
    }

    fun setLicenseText(newLicenseText: List<String?>) {
        licenseText.value = newLicenseText
    }
}