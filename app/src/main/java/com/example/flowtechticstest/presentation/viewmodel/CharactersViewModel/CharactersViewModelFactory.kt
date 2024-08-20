package com.example.flowtechticstest.presentation.viewmodel.CharactersViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flowtechticstest.data.repo.CharactersRepo

class CharactersViewModelFactory (private val repository: CharactersRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            CharactersViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}