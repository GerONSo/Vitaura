package com.example.vitaura.special

import com.squareup.moshi.Json

data class ActionsData(@field:Json(name = "data") var data: List<Action>)

data class Action(@field:Json(name = "attributes") var attrs: ActionAttributes)

data class ActionAttributes(
    @field:Json(name = "title") var title: String,
    @field:Json(name = "path") var path: ActionPath,
    @field:Json(name = "body") var body: ActionBody
)

data class ActionPath(@field:Json(name = "alias") var alias: String)

data class ActionBody(@field:Json(name = "processed") var value: String)