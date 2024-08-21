package com.example.flowtechticstest.data.model.characters_response

import kotlinx.serialization.SerialName

data class CharacterResponse(
    val info: Info,
    @SerialName("results") val results: List<CharacterResult>
)