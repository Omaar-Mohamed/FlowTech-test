package com.example.flowtechticstest.data.repo

import com.example.flowtechticstest.data.model.characters_response.CharacterResponse
import com.example.flowtechticstest.data.source.remote.AppReomteDataSourse
import kotlinx.coroutines.flow.Flow

class CharacterRepoImpl(
    private val appRemoteDataSourse: AppReomteDataSourse
) : CharactersRepo{

    companion object{
        private var INSTANCE: CharacterRepoImpl? = null
        fun getInstance(appRemoteDataSourse: AppReomteDataSourse): CharacterRepoImpl {
            if (INSTANCE == null) {
                INSTANCE = CharacterRepoImpl(appRemoteDataSourse)
            }
            return INSTANCE!!
        }
    }
    override suspend fun getCharacters(page:Int): Flow<CharacterResponse> {
        return appRemoteDataSourse.getCharacters(page)
    }
}