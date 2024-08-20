package com.example.flowtechticstest.data.source.remote

import com.example.flowtechticstest.data.model.characters_response.CharacterResponse
import com.example.flowtechticstest.data.source.remote.RetrofitHelper.retrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object AppRemoteDataSourseImpl: AppReomteDataSourse{
    override suspend fun getCharacters(): Flow<CharacterResponse> = flow {
        val characterResponse = retrofitInstance.create(NetworkServices::class.java).getCharacters()
        emit(characterResponse)
    }
}