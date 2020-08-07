package com.example.vitaura.send_review

import com.squareup.moshi.Json


data class Problem (
    @field:Json(name = "title") var title: String,
    @field:Json(name = "field_photo") var photo: String,
    @field:Json(name = "field_services") var services: String
)
