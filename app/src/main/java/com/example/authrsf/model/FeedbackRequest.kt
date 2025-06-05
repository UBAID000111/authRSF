package com.example.authrsf.model

import retrofit2.http.Query

data class FeedbackRequest (
    val name: String,
     val query: String,
    val description: String
)