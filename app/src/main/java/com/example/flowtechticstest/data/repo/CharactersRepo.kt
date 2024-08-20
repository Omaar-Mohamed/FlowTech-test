package com.example.flowtechticstest.data.repo

import com.example.flowtechticstest.data.model.characters_response.CharacterResponse
import kotlinx.coroutines.flow.Flow

interface CharactersRepo {
    suspend fun getCharacters(): Flow<CharacterResponse>
}