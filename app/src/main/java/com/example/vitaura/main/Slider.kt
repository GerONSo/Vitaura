package com.example.vitaura.main

import com.squareup.moshi.Json

data class SliderData(@field:Json(name = "data") var data: List<Slider>)

data class Slider(
    @field:Json(name = "attributes") var attrs: SliderAttrs,
    @field:Json(name = "relationships") var relationships: SliderRelationships
)

data class SliderAttrs(
    @field:Json(name = "title") var title: String,
    @field:Json(name = "body") var body: SliderBody,
    @field:Json(name = "field_link") var link: String
)

data class SliderBody(@field:Json(name = "value") var value: String)

data class SliderRelationships(@field:Json(name = "field_photo") var photo: SliderPhotoData)

data class SliderPhotoData(@field:Json(name = "data") var data: SliderPhotoId)

data class SliderPhotoId(@field:Json(name = "id") var id: String)