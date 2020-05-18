package com.example.vitaura.about

import android.text.Html
import android.util.Log
import org.jsoup.Jsoup

object AboutDataParser {
    fun parseAbout(text: String, title: String) {
        var doc = Jsoup.parse(text)
        val elements = doc.getElementsByTag("p")
        val titleElements = doc.getElementsByTag("h2")
        val listElements = doc.getElementsByTag("ul")
        val title1 = titleElements[0].text()
        val title2 = titleElements[1].text()
//        Log.d("test", elements.toString())
        val image1 = elements[0].getElementsByTag("img")
        val image1Link = image1[0].attr("src")
        val intro: String = elements[1].toString().replace("\n", "") + "\n\n" +
                elements[2].toString().replace("\n", "") + "\n\n" +
                elements[3].toString().replace("\n", "") + "\n\n" +
                elements[4].toString().replace("\n", "")
        val patients: String = elements[5].toString().replace("\n", "") + "\n\n" +
                elements[6].toString().replace("\n", "") + "\n\n" +
                elements[7].toString().replace("\n", "") + "\n\n" +
                elements[8].toString().replace("\n", "")
        val list2: String = listElements[0]
            .toString()
            .replace("\n", "")
            .replace("<li>", "<p>–\t ")
            .replace("</li>", "</p>")
            .replace("<ul>", "")
            .replace("</ul>", "")
        val list: String = Html.fromHtml(elements[10].toString()).toString() + "\n\n" + list2
//        Log.d("test", list2)
        val image2 = elements[9].getElementsByTag("img")
        val image2Link = image2.attr("src")
        AboutDataRepository.setAboutImages(listOf(image1Link, image2Link))
        AboutDataRepository.setAboutText(listOf(intro, patients, list))
        AboutDataRepository.setAboutTitles(listOf(title, title1, title2))
    }

    fun parseLicenseEmail(text: String?) = text
            ?.replace("<a href=\"mailto:zdrav@mos.ru\">", "")
            ?.replace("</a>", "")
            ?.replace("<a href=\"mailto:uprav@77.rospotrebnadzor.ru\">", "")
            ?.replace("<a href=\"mailto:office@reg77.roszdravnadzor.ru\">", "")
}