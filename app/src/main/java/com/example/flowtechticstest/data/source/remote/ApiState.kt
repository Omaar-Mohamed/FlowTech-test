package com.example.flowtechticstest.data.source.remote

import com.example.flowtechticstest.data.model.characters_response.CharacterResponse

sealed class ApiState  {
    object Loading : ApiState()
    data class Success(val data: CharacterResponse) : ApiState()
    data class Failure(val error: Throwable) : ApiState()
}