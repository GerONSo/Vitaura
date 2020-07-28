package com.example.vitaura.helpers

object HtmlNormalizer {
    fun normalize(input: String?): String? {
        val text = input
            ?.replace("<p>", "")
            ?.replace("</p>", "<br/><br/>")
            ?.replace("<li>", "-    ")
            ?.replace("</li>", "<br/>")
            ?.replace("<ul>", "")
            ?.replace("</ul>", "")
            ?.replace("\n", "")
//            ?.replace("</ul>", "")
            ?.replace("\r", "")
            ?.replace("src=\"", "src=\"vitaura-clinic.ru")
            ?.let {
                if(it.length >= 10) it.substring(0, it.length - 10)
                else it
            }

//        return if(text != null) Html.fromHtml(text).toString() else ""
//        return if(text != null) Html.fromHtml("<h2>anime</h2>text").toString() else ""
        return text
    }

    fun normalizeAbout(input: String?): String? {
        val text = input
            ?.replace("<p>–", "<br/>–")
            ?.replace("<p>", "")
            ?.replace("</p>", "")
            ?.replace("<li>", "-    ")
            ?.replace("</li>", "")
            ?.replace("<ul>", "")
            ?.replace("</ul>", "")
            ?.replace("\n", "")
//            ?.replace("</ul>", "")
            ?.replace("\r", "")

//        return if(text != null) Html.fromHtml(text).toString() else ""
//        return if(text != null) Html.fromHtml("<h2>anime</h2>text").toString() else ""
        return text
    }

    fun normalizeLicense(input: String?): String? {
        return input
            ?.replace("<p>", "")
            ?.replace("</p>", "<br/>")
            ?.replace("\n", "")
    }
}