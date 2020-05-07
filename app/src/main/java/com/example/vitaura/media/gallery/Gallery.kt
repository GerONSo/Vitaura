package com.example.vitaura.media.gallery

import com.squareup.moshi.Json

data class GalleryData(@field:Json(name = "data") var data: List<Gallery>)

data class Gallery(@field:Json(name = "attributes") var attrs: GalleryAttributes,
                   @field:Json(name = "relationships") var relationships: GalleryRelationships)

data class GalleryAttributes(@field:Json(name = "title") var title: String)

data class GalleryRelationships(@field:Json(name = "field_gallery_image") var galleryImageField: GalleryImageField)

data class GalleryImageField(@field:Json(name = "data") var data: List<GalleryFile>)

data class GalleryFileData(@field:Json(name = "data") var data: List<GalleryFile>)

data class GalleryFile(@field:Json(name = "id") var id: String,
                       @field:Json(name = "attributes") var attrs: GalleryFileAttributes)

data class GalleryFileAttributes(@field:Json(name = "uri") var uri: GalleryFileUri)

data class GalleryFileUri(@field:Json(name = "url") var url: String)