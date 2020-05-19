package com.example.vitaura.special

import com.squareup.moshi.Json

data class Special(@field:Json(name = "title") var name: String,
                   @field:Json(name = "body") var body: String,
                   @field:Json(name = "field_image") var imagePreviewPath: String,
                   @field:Json(name = "view_node") var id: String)