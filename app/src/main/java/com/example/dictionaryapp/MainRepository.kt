package com.example.dictionaryapp

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.example.dictionaryapp.database.AppDatabase
import com.example.dictionaryapp.database.models.SynonymsAntonyms
import com.example.dictionaryapp.database.models.TYPE
import com.example.dictionaryapp.network.DictionaryApiService
import com.example.dictionaryapp.network.RetrofitBuilder
import com.example.dictionaryapp.network.data.Definition
import com.example.dictionaryapp.network.data.Meaning
import com.example.dictionaryapp.network.data.Word
import java.lang.Exception
import com.example.dictionaryapp.database.models.Word as WordDB
import com.example.dictionaryapp.database.models.Meaning as MeaningDB
import com.example.dictionaryapp.database.models.Definition as DefinitionDB

class MainRepository(private val database: AppDatabase){
    private val dictionaryApiService:DictionaryApiService

    init {
        dictionaryApiService = RetrofitBuilder.dictionaryApiService
    }

    suspend fun getItorSetIt(word: String):Word{
        val wordFromDatabase = getWordFromDatabaseToApi(word)
        if(wordFromDatabase != null){
            return wordFromDatabase
        }else{
            try {
                val wordFromApi = getWord(word)
                insertWordFromApitoDatabase(wordFromApi[0])
                return wordFromApi[0]
            }catch (constraint: SQLiteConstraintException){
                throw constraint
            }catch (error:Exception){
                throw error("Error in api")
            }
        }
    }

    private suspend fun getWordFromDatabaseToApi(word: String): Word? {
        val wordDb = getWordDatabase(word)
        if (wordDb != null) {
            val meaningDb = getMeaningByWordIdDatabase(wordDb.id!!)
            val meaningList = mutableListOf<Meaning>()
            for (mean in meaningDb) {
                var definitionDb = getDefinitionByMeaningIdDatabase(mean.id!!)
                var syn = getSynonums(mean.id)
                var ant = getAntonyms(mean.id)
                var definitionApi = Definition(
                    definitionDb.definition,
                    definitionDb.example,
                    syn ?: emptyList<String>(),
                    ant ?: emptyList<String>()
                )
                var meaningApi = Meaning(mean.partOfSpeech, listOf(definitionApi))
                meaningList.add(meaningApi)
            }
            val wordApi = Word(wordDb.word, wordDb.phonetic, wordDb.origin, meaningList)
            return wordApi
        } else return null
    }

    private suspend fun insertWordFromApitoDatabase(word: Word): Boolean {
        val word_id = insertWordDatabase(
            WordDB(
                id = null,
                word = word.word,
                phonetic = word.phonetic,
                origin = word.origin
            )
        )
        if (word_id != null) {
            if (word.meanings.isNotEmpty()) {
                for (mean in word.meanings) {
                    var mean_id = insertMeaningDatabase(
                        MeaningDB(
                            id = null,
                            partOfSpeech = mean.partOfSpeech,
                            word_id = word_id
                        )
                    )
                    if (mean_id != null) {
                        var def = mean.definitions[0]
                        insertDefinitionDatabase(
                            DefinitionDB(
                                null,
                                def.definition,
                                def.example,
                                mean_id
                            )
                        )
                        if(mean.definitions[0].synonyms.isNotEmpty()){
                            for(syn in mean.definitions[0].synonyms){
                                insertSynonymAntonyms(
                                    SynonymsAntonyms(null,mean_id,
                                        TYPE.SYNONYMS,syn)
                                )
                            }
                        }
                        if(mean.definitions[0].antonyms.isNotEmpty()){
                            for(ant in mean.definitions[0].antonyms){
                                insertSynonymAntonyms(
                                    SynonymsAntonyms(null,mean_id,
                                        TYPE.ANTONYMS,ant)
                                )
                            }
                        }
                    }
                }
            }
            return true
        }
        return false
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

    suspend fun getALlWords():List<String>{
        return database.wordDao().getAllWords()
    }

    suspend fun deleteALlWords(){
        database.wordDao().deleAll()
    }

    suspend fun getWord(word:String):List<Word>{
        return dictionaryApiService.getWord(word)
    }
}