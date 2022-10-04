package com.example.artsproject.data

import com.example.artsproject.core.IoDispatcher
import com.example.artsproject.data.client.ApiArt
import com.example.artsproject.data.dto.Art
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ArtRepository @Inject constructor(
    private val api: ApiArt,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getArts(): Result<List<Art>> {

        return withContext(dispatcher) {

            val result = api.getArts()

            return@withContext result.map { it.data }
        }
    }
}