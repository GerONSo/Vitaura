package com.example.vitaura.about

import com.squareup.moshi.Json
import java.util.jar.Attributes

data class Pages(@field:Json(name = "data") var data: List<Page>)

data class Page(@field:Json(name = "attributes") var attributes: PageBody)

data class PageBody(@field:Json(name = "body") var body: AboutData,
                    @field:Json(name = "title") var title: String)

data class AboutData(@field:Json(name = "processed") var text: String)