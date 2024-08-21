package com.example.flowtechticstest.data.source.remote

import com.example.flowtechticstest.data.model.characters_response.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkServices {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharacterResponse
}
