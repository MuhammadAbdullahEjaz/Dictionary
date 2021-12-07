package com.example.dictionaryapp

import android.util.Log
import androidx.lifecycle.*
import com.example.dictionaryapp.network.data.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private var word: String? = null

    private val _resultVisibility = MutableLiveData(false)
    val resultVisibility: LiveData<Boolean> get() = _resultVisibility

    private val _errorVisibility = MutableLiveData(false)
    val errorVisibility: LiveData<Boolean> get() = _errorVisibility

    private val _result = MutableLiveData<Word>()
    val result: LiveData<Word> get() = _result

    fun onSearch() {
        if (!word.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val resp = repository.getWord(word!!)
                    Log.d("fetch", "response is ${resp[0]}")
                    _result.postValue(resp[0])
                    _errorVisibility.postValue(false)
                    _resultVisibility.postValue(true)
                } catch (e: Exception) {
                    _resultVisibility.postValue(false)
                    _errorVisibility.postValue(true)
                    Log.d("fetch", "exception is ${e.message}")
                }
            }
        }
    }

    fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        word = text.toString()
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