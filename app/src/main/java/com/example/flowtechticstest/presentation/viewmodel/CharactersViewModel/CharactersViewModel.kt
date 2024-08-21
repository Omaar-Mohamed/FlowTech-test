package com.example.flowtechticstest.presentation.viewmodel.CharactersViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowtechticstest.data.model.characters_response.CharacterResponse
import com.example.flowtechticstest.data.repo.CharactersRepo
import com.example.flowtechticstest.data.source.remote.ApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val charactersRepo: CharactersRepo
) : ViewModel() {

    private val _allCharacters: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Loading)
    val allCharacters: StateFlow<ApiState> = _allCharacters

     var currentPage = 1
     var totalPages = 42 // Default value based on API response metadata
     var isLoading = false

    fun loadNextPage() {
        if (isLoading || currentPage >= totalPages) return

        isLoading = true
        viewModelScope.launch {
            _allCharacters.value = ApiState.Loading

            charactersRepo.getCharacters(currentPage + 1)
                .catch { e ->
                    isLoading = false
                    _allCharacters.value = ApiState.Failure(e)
                }
                .collect { response ->
                    totalPages = response.info.pages
                    currentPage++ // Increment only after a successful load

                    val currentList = (_allCharacters.value as? ApiState.Success)?.data?.results ?: emptyList()
                    val updatedList = currentList + response.results

                    _allCharacters.value = ApiState.Success(response.copy(results = updatedList))
                    isLoading = false
                }
        }
    }

    fun loadPreviousPage() {
        if (isLoading || currentPage <= 1) return

        isLoading = true
        viewModelScope.launch {
            _allCharacters.value = ApiState.Loading

            charactersRepo.getCharacters(currentPage - 1)
                .catch { e ->
                    isLoading = false
                    _allCharacters.value = ApiState.Failure(e)
                }
                .collect { response ->
                    currentPage-- // Decrement only after a successful load

                    val currentList = (_allCharacters.value as? ApiState.Success)?.data?.results ?: emptyList()
                    val updatedList = response.results + currentList

                    _allCharacters.value = ApiState.Success(response.copy(results = updatedList))
                    isLoading = false
                }
        }
    }

    fun refresh() {
        currentPage = 1
        isLoading = false
        _allCharacters.value = ApiState.Loading

        viewModelScope.launch {
            charactersRepo.getCharacters(currentPage)
                .catch { e ->
                    _allCharacters.value = ApiState.Failure(e)
                }
                .collect { response ->
                    totalPages = response.info.pages
                    _allCharacters.value = ApiState.Success(response)
                }
        }
    }
}
