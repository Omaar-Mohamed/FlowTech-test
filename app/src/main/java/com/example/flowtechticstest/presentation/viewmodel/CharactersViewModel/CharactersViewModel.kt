package com.example.flowtechticstest.presentation.viewmodel.CharactersViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowtechticstest.data.model.characters_response.CharacterResponse
import com.example.flowtechticstest.data.repo.CharactersRepo
import com.example.flowtechticstest.data.source.remote.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val charactersRepo: CharactersRepo
) :ViewModel() {
    private val _allCharacters : MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Loading)
    val allCharacters = _allCharacters

    fun getCharacters(){
        viewModelScope.launch {
            charactersRepo.getCharacters().catch { e->
                _allCharacters.value = ApiState.Failure(e)
            }.collect {
                _allCharacters.value  = ApiState.Success(it)
            }
        }
    }
}