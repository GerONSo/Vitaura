package com.example.vitaura.send_review

import com.squareup.moshi.Json

data class ProblemData(@field:Json(name = "data") var data: List<Problem>)

data class Problem(
    @field:Json(name = "attributes") var attrs: ProblemAttributes,
    @field:Json(name = "relationships") var relationships: ProblemRelationships
)

data class ProblemAttributes(
    @field:Json(name = "title") var title: String,
    @field:Json(name = "path") var path: ProblemPath
)

data class ProblemPath(@field:Json(name = "alias") var alias: String)

data class ProblemRelationships(@field:Json(name = "field_photo") var data: ProblemPhotoData)

data class ProblemPhotoData(@field:Json(name = "data") var data: ProblemPhoto)

data class ProblemPhoto(@field:Json(name = "id") var id: String)

