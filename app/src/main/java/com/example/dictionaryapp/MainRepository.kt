package com.example.dictionaryapp

import com.example.dictionaryapp.network.DictionaryApiService
import com.example.dictionaryapp.network.RetrofitBuilder
import com.example.dictionaryapp.network.data.Word

class MainRepository{
    private val dictionaryApiService:DictionaryApiService

    init {
        dictionaryApiService = RetrofitBuilder.dictionaryApiService
    }

    suspend fun getWord(word:String):List<Word>{
        return dictionaryApiService.getWord(word)
    }

}