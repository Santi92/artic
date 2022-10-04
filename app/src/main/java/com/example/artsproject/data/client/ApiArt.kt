package com.example.artsproject.data.client

import com.example.artsproject.data.dto.ResponseArt
import retrofit2.http.GET

interface ApiArt {

    @GET("artworks?limit=20")
    suspend fun getArts(): Result<ResponseArt>

}