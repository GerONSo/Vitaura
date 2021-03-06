package com.example.vitaura.media.video

import com.squareup.moshi.Json

data class VideoData(@field:Json(name = "data") var data: List<Video>)

data class Video(@field:Json(name = "attributes") var attrs: VideoAttrs)

data class VideoAttrs(@field:Json(name = "title") var title: String,
                      @field:Json(name = "field_youtube") var link: VideoLink?,
                      @field:Json(name = "body") var body: VideoBody
)

data class VideoBody(@field:Json(name = "processed") var value: String)

data class VideoLink(@field:Json(name = "video_id") var id: String)


