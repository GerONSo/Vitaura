package com.example.vitaura.send_review

import com.example.vitaura.helpers.GMailSender
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object SendReviewRepository {
    lateinit var openSendReviewResult: (tag: String) -> Unit

    fun getReviewBody(name: String?, phone: String?, review: String?): String =
                "Имя: $name\n" +
                "Телефон: $phone\n" +
                "Отзыв: $review"

    fun getLoginBody(name: String?, phone: String?, email: String?, date: String?, comment: String?): String =
                "Имя: $name\n" +
                "Телефон: $phone\n" +
                "Email: $email\n" +
                "Удобная дата: $date\n" +
                "Комментарий: $comment"

    fun sendEmail(subject: String, body: String) {
        GlobalScope.launch {
            val sender = GMailSender(
                "vitauraclinic.informator@gmail.com",
                "4%9jc#2Xuv"
            )
            sender.sendMail(
                subject,
                body,
                "vitauraclinic.informator@gmail.com",
                "sergon2002@gmail.com"
//                "mcsimov@gmail.com,pr.empire14@gmail.com,mila2863@mail.ru,info@vitaura-clinic.ru,s.v.dmitrienko@yandex.ru,marina.melnikova16@inbox.ru"
            )
        }
    }
}