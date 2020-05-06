package com.example.vitaura.media

import com.squareup.moshi.Json

data class VideoData(@field:Json(name = "data") var data: List<Video>)

data class Video(@field:Json(name = "attributes") var attrs: VideoAttrs)

data class VideoAttrs(@field:Json(name = "title") var title: String,
                      @field:Json(name = "field_youtube") var link: VideoLink?)

data class VideoLink(@field:Json(name = "video_id") var id: String)


data class GalleryData(@field:Json(name = "data") var data: List<Gallery>)

data class Gallery(@field:Json(name = "title") var title: String)