package com.example.authrsf.model

import android.app.wallpaper.WallpaperDescription
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.Part

interface ApiService {
    @POST("/api/user/signup")
    fun signupUser(@Body request: UserRequest): Call<UserResponse>
    @POST("/api/user/login")
    fun loginUser(@Body request: UserRequest): Call<UserResponse>

    @GET("internships")
    fun getInternships():Call<List<Internship>>


    @Multipart
    @POST("/api/applications/apply")
    fun applyInternship(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("internshipTitle") internshipTitle: RequestBody,
        @Part("skills") skills: RequestBody,
        @Part("areaOfInterest") areaOfInterest: RequestBody,
        @Part("description") description: RequestBody,
        @Part resume: MultipartBody.Part
    ): Call<ApiResponse>
}