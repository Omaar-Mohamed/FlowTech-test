package com.example.flowtechticstest.data.source.remote

import com.example.flowtechticstest.data.model.characters_response.CharacterResponse
import kotlinx.coroutines.flow.Flow

interface AppReomteDataSourse {
    suspend fun getCharacters(): Flow<CharacterResponse>
}