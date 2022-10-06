package com.example.artsproject.data.client

import com.example.artsproject.data.dto.ResponseArt
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiArt {

    @GET("artworks?limit=20")
    suspend fun getArts(@Query("page") page: String): Result<ResponseArt>

}