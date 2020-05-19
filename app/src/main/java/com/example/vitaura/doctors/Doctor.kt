package com.example.vitaura.doctors

import com.squareup.moshi.Json

data class Doctor(@field:Json(name = "name") var name: String,
                  @field:Json(name = "spec") var spec: String,
                  @field:Json(name = "photo_name") var photoName: String,
                  @field:Json(name = "mini_description") var miniDescription: String,
                  @field:Json(name = "description") var description: String)