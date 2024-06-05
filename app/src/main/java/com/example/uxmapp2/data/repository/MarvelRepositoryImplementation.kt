package com.example.uxmapp2.data.repository

import android.util.Log
import com.example.uxmapp2.data.characterData.Hero
import com.example.uxmapp2.data.MarvelApi
import com.example.uxmapp2.domain.repository.MarvelRepository
import javax.inject.Inject

class MarvelRepositoryImplementation @Inject constructor(
    private val api: MarvelApi
) : MarvelRepository {
    override suspend fun getAllCharacters(nameStartsWith: String): Hero {
        Log.d("MarvelRepository", "Fetching heroes with search: $nameStartsWith")
        try {
            val response = api.getAllCharacters(search = nameStartsWith)
            Log.d("MarvelRepository", "Response: ${response.data.results}")
            println("Network Response: $response") // Add this line to log the network response
            return response
        } catch (e: Exception) {
            Log.e("MarvelRepository", "Error fetching heroes", e)
            throw e
        }
    }
}