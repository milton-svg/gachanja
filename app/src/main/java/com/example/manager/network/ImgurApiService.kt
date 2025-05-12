package com.example.manager.network

import com.example.manager.models.ImgurResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ImgurService {
    @Multipart
    @POST("image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Header("Authorization") auth: String
    ): Response<ImgurResponse>
}
