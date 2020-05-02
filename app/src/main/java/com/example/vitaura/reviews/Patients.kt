package com.example.vitaura.reviews

import com.squareup.moshi.Json

data class Patients(@field:Json(name = "data") var data: List<Patient>)

data class Patient(@field:Json(name = "attributes") var attributes: Attribute)

data class Attribute(@field:Json(name = "title") var title: String,
                     @field:Json(name = "field_feedback_text") var text: Text
)

data class Text(@field:Json(name = "processed") var value: String)