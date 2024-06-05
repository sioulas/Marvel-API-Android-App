package com.example.uxmapp2.data

import com.example.uxmapp2.source.Constants
import com.example.uxmapp2.data.characterData.Hero
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters")
    suspend fun getAllCharacters(
        @Query("apikey") apikey: String = Constants.PUBLIC_KEY,
        @Query("ts") ts: String = Constants.timeStamp,
        @Query("hash") hash: String = Constants.hash(),
        @Query("nameStartsWith") search: String
    ): Hero
}

