package com.example.uxmapp2.ui.heroes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uxmapp2.data.characterData.Result
import com.example.uxmapp2.source.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor() : ViewModel() {

    private val _heroes = MutableLiveData<State<List<Result>>>()
    val heroes: LiveData<State<List<Result>>> = _heroes

    fun getSavedHeroes() {
        _heroes.value = State.Loading
        _heroes.value = State.Success(SavedHeroesManager.getSavedHeroes())
    }

}







