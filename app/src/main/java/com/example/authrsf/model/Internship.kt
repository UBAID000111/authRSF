package com.example.authrsf.model

import com.google.gson.annotations.SerializedName
data class Internship(
    @SerializedName("InternshipType")
    val type: String,

    @SerializedName("InternshipLocation")
    val location: String,

    @SerializedName("InternshipDuration")
    val duration: String,

    @SerializedName("InternshipDescription")
    val description: String,

    @SerializedName("InternshipRequirements")
    val requirements: String
)