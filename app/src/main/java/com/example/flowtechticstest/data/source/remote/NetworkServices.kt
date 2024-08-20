package com.example.flowtechticstest.data.source.remote

import com.example.flowtechticstest.data.model.characters_response.CharacterResponse
import retrofit2.http.GET

interface NetworkServices {
    @GET("character")
    suspend fun getCharacters(): CharacterResponse
}