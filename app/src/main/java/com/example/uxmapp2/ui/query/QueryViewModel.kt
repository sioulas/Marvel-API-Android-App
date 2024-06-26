package com.example.uxmapp2.ui.query

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uxmapp2.data.characterData.Result
import com.example.uxmapp2.domain.repository.MarvelRepository
import com.example.uxmapp2.source.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QueryViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel() {

    private val _searchResults = MutableLiveData<State<List<Result>>>()
    val searchResults: LiveData<State<List<Result>>> = _searchResults

    init {
        fetchAllHeroes()
    }

    fun fetchAllHeroes() {
        _searchResults.value = State.Loading
        viewModelScope.launch {
            try {
                val response = repository.getAllCharacters("")
                _searchResults.value = State.Success(response.data.results)
            } catch (e: Exception) {
                _searchResults.value = State.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun searchHeroes(nameStartsWith: String) {
        _searchResults.value = State.Loading
        viewModelScope.launch {
            try {
                val response = repository.getAllCharacters(nameStartsWith)
                _searchResults.value = State.Success(response.data.results)
            } catch (e: Exception) {
                _searchResults.value = State.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}


















