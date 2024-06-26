package com.example.uxmapp2.data.repository

import android.util.Log
import com.example.uxmapp2.data.characterData.Hero
import com.example.uxmapp2.data.MarvelApi
import com.example.uxmapp2.domain.repository.MarvelRepository
import com.example.uxmapp2.source.Constants
import javax.inject.Inject

class MarvelRepositoryImplementation @Inject constructor(
    private val api: MarvelApi
) : MarvelRepository {
    override suspend fun getAllCharacters(nameStartsWith: String): Hero {
        Log.d("MarvelRepository", "Fetching heroes with search: $nameStartsWith")
        try {
            val response = if (nameStartsWith.isEmpty()) {
                api.getAllCharacters(apikey = Constants.PUBLIC_KEY, ts = Constants.timeStamp, hash = Constants.hash())
            } else {
                api.getAllCharacters(apikey = Constants.PUBLIC_KEY, ts = Constants.timeStamp, hash = Constants.hash(), search = nameStartsWith)
            }
            Log.d("MarvelRepository", "Response: ${response.data.results}")
            println("Network Response: $response")
            return response
        } catch (e: Exception) {
            Log.e("MarvelRepository", "Error fetching heroes", e)
            throw e
        }
    }
}


