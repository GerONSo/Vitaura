package com.example.vitaura.doctors

import com.squareup.moshi.Json

data class Doctor(@field:Json(name = "name") var name: String,
                  @field:Json(name = "spec") var spec: String,
                  @field:Json(name = "photo_name") var photoName: String,
                  @field:Json(name = "mini_description") var miniDescription: String,
                  @field:Json(name = "description") var description: String)

data class DoctorsData(@field:Json(name = "data") var data: List<Doctors>)

data class Doctors(
    @field:Json(name = "relationships") var relationships: DoctorRelationships,
    @field:Json(name = "attributes") var attrs: DoctorAttributes
)

data class DoctorAttributes(
    @field:Json(name = "title") var title: String,
    @field:Json(name = "body") var body: DoctorTextField,
    @field:Json(name = "field_mobile_education") var education: DoctorTextField,
    @field:Json(name = "field_mobile_information") var information: DoctorTextField,
    @field:Json(name = "field_mobile_short_description") var shortDescription: DoctorTextField,
    @field:Json(name = "field_mobile_specialization") var specialization: DoctorTextField,
    @field:Json(name = "field_post") var post: String
)

data class DoctorTextField(@field:Json(name = "value") var value: String)

data class DoctorRelationships(@field:Json(name = "field_services") var services: DoctorServiceData)

data class DoctorServiceData(@field:Json(name = "data") var data: List<DoctorService>)

data class DoctorService(@field:Json(name = "id") var id: String)

data class DoctorItem(
    var title: String,
    var body: DoctorTextField,
    var education: DoctorTextField,
    var information: DoctorTextField,
    var shortDescription: DoctorTextField,
    var specialization: DoctorTextField,
    var post: String
)