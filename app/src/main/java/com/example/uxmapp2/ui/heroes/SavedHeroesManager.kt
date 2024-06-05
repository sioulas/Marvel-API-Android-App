package com.example.uxmapp2.ui.heroes


import com.example.uxmapp2.data.characterData.Result

object SavedHeroesManager {
    private val savedHeroes = mutableListOf<Result>()

    fun addHero(hero: Result) {
        savedHeroes.add(hero)
    }

    fun isHeroSaved(hero: Result): Boolean {
        return savedHeroes.any { it.id == hero.id }
    }

    fun getSavedHeroes(): List<Result> {
        return savedHeroes
    }
}
