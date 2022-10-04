package com.example.artsproject.data.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientRetrofit {

    fun getClient(): ApiArt{

        val client = Retrofit.Builder()
            .baseUrl("https://api.artic.edu/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return client.create(ApiArt::class.java)
    }


}