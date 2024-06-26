package com.example.uxmapp2.domain.repository

import com.example.uxmapp2.data.characterData.Hero



interface MarvelRepository {
    suspend fun getAllCharacters(nameStartsWith: String): Hero

}