package com.example.dictionaryapp

import android.util.Log
import androidx.lifecycle.*
import com.example.dictionaryapp.database.models.TYPE
import com.example.dictionaryapp.network.data.Definition
import com.example.dictionaryapp.network.data.Meaning
import com.example.dictionaryapp.network.data.Word
import com.example.dictionaryapp.database.models.Word as WordDB
import com.example.dictionaryapp.database.models.Meaning as MeaningDB
import com.example.dictionaryapp.database.models.Definition as DefinitionDB
import com.example.dictionaryapp.database.models.SynonymsAntonyms as Sa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private var words: String? = null

    private val _resultVisibility = MutableLiveData(false)
    val resultVisibility: LiveData<Boolean> get() = _resultVisibility

    private val _errorVisibility = MutableLiveData(false)
    val errorVisibility: LiveData<Boolean> get() = _errorVisibility

    private val _loadingVisibility = MutableLiveData(false)
    val loadingVisibility: LiveData<Boolean> get() = _loadingVisibility

    private val _result = MutableLiveData<Word>()
    val result: LiveData<Word> get() = _result

    fun onSearch() {
        if (!words.isNullOrEmpty()) {
            _loadingVisibility.postValue(true)
            _resultVisibility.postValue(false)
            _errorVisibility.postValue(false)
            CoroutineScope(Dispatchers.IO).launch {
                val wordApi = getWordFromDatabaseToApi(words!!)
                if (wordApi != null) {
                    _result.postValue(wordApi!!)
                    _errorVisibility.postValue(false)
                    _resultVisibility.postValue(true)
                    _loadingVisibility.postValue(false)
                    Log.d("fetch", "data from db")
                } else {
                    try {
                        val resp = repository.getWord(words!!)
                        _result.postValue(resp[0])
                         insertWordFromApitoDatabase(resp[0])
                        _errorVisibility.postValue(false)
                        _resultVisibility.postValue(true)
                        _loadingVisibility.postValue(false)
                        Log.d("fetch", "data from api ${resp[0]}")
                    } catch (e: Exception) {
                        _resultVisibility.postValue(false)
                        _errorVisibility.postValue(true)
                        _loadingVisibility.postValue(false)
                        Log.d("fetch", "exception in api call ${e.message}")
                    }
                }
            }
        }
    }

    suspend fun getWordFromDatabaseToApi(word: String): Word? {
        val wordDb = repository.getWordDatabase(word)
        if (wordDb != null) {
            val meaningDb = repository.getMeaningByWordIdDatabase(wordDb.id!!)
            val meaningList = mutableListOf<Meaning>()
            for (mean in meaningDb) {
                var definitionDb = repository.getDefinitionByMeaningIdDatabase(mean.id!!)
                var syn = repository.getSynonums(mean.id)
                var ant = repository.getAntonyms(mean.id)
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

    suspend fun insertWordFromApitoDatabase(word: Word): Boolean {
        val word_id = repository.insertWordDatabase(
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
                    var mean_id = repository.insertMeaningDatabase(
                        MeaningDB(
                            id = null,
                            partOfSpeech = mean.partOfSpeech,
                            word_id = word_id
                        )
                    )
                    if (mean_id != null) {
                        var def = mean.definitions[0]
                        repository.insertDefinitionDatabase(
                            DefinitionDB(
                                null,
                                def.definition,
                                def.example,
                                mean_id
                            )
                        )
                        if(mean.definitions[0].synonyms.isNotEmpty()){
                            for(syn in mean.definitions[0].synonyms){
                                repository.insertSynonymAntonyms(Sa(null,mean_id,TYPE.SYNONYMS,syn))
                            }
                        }
                        if(mean.definitions[0].antonyms.isNotEmpty()){
                            for(ant in mean.definitions[0].antonyms){
                                repository.insertSynonymAntonyms(Sa(null,mean_id,TYPE.ANTONYMS,ant))
                            }
                        }
                    }
                }
            }
            return true
        }
        return false
    }

    fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        if(text.isEmpty()){
            _resultVisibility.postValue(false)
            _errorVisibility.postValue(false)
        }
        words = text.toString()
    }

}

class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown viewmodel class")
        }
    }

}