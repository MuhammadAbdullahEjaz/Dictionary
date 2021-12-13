package com.example.dictionaryapp

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(val repository: MainRepository): ViewModel() {

    private val _data = MutableLiveData<List<String>>()
    val data:LiveData<List<String>> get() = _data

    fun getAllWords(){
        viewModelScope.launch {
            val words = repository.getALlWords()
            Log.d("fetch", "data is $words")
            _data.postValue(words)
        }
    }

}

class WordViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown viewmodel class")
        }
    }

}