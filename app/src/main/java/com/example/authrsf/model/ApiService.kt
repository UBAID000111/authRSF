package com.example.authrsf.model

import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface ApiService {
    @POST("/api/user/signup")
    fun signupUser(@Body request: UserRequest): Call<UserResponse>
    @POST("/api/user/login")
    fun loginUser(@Body request: UserRequest): Call<UserResponse>

    @GET("/internships")
    fun getInternships():Call<List<Internship>>
}