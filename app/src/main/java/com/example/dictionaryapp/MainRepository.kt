package com.example.dictionaryapp

import com.example.dictionaryapp.database.AppDatabase
import com.example.dictionaryapp.database.models.SynonymsAntonyms
import com.example.dictionaryapp.network.DictionaryApiService
import com.example.dictionaryapp.network.RetrofitBuilder
import com.example.dictionaryapp.network.data.Word
import com.example.dictionaryapp.database.models.Word as WordDB
import com.example.dictionaryapp.database.models.Meaning as MeaningDB
import com.example.dictionaryapp.database.models.Definition as DefinitionDB

class MainRepository(private val database: AppDatabase){
    private val dictionaryApiService:DictionaryApiService

    init {
        dictionaryApiService = RetrofitBuilder.dictionaryApiService
    }

    suspend fun getWordDatabase(word:String):WordDB?{
        return database.wordDao().getWord(word)
    }
    suspend fun insertWordDatabase(word:WordDB):Long?{
        return database.wordDao().putWord(word)
    }

    suspend fun getMeaningByWordIdDatabase(id:Long):List<MeaningDB>{
        return database.meaningDao().getMeaningByWordId(id)
    }

    suspend fun insertMeaningDatabase(meaning: MeaningDB):Long?{
        return database.meaningDao().putMeaning(meaning)
    }

    suspend fun getDefinitionByMeaningIdDatabase(id:Long):DefinitionDB{
        return database.definitionDao().getDefinitionByMeaningId(id)
    }

    suspend fun insertDefinitionDatabase(definition:DefinitionDB){
        database.definitionDao().putDefinition(definition)
    }

    suspend fun getSynonums(id:Long):List<String>?{
        return database.synonymsantonymsDao().getSynonyms(id)
    }

    suspend fun getAntonyms(id:Long):List<String>?{
        return database.synonymsantonymsDao().getAntonyms(id)
    }

    suspend fun insertSynonymAntonyms(synonymsAntonyms: SynonymsAntonyms){
        database.synonymsantonymsDao().putEntry(synonymsAntonyms)
    }

    suspend fun getWord(word:String):List<Word>{
        return dictionaryApiService.getWord(word)
    }

}