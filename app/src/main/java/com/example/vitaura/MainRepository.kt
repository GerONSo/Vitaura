package com.example.vitaura

import com.example.vitaura.send_review.SendReviewViewModel

object MainRepository {
    var currentSendReviewTab = SendReviewViewModel.SEND_REVIEW
    lateinit var openSendReviewFragment: () -> Unit
}