package com.example.dictionaryapp.network

import com.example.dictionaryapp.network.data.Word
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService{
    @GET("api/v2/entries/en/{word}")
    suspend fun getWord(@Path("word") word:String): List<Word>
}
