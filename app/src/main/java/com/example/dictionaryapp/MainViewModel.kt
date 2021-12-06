package com.example.dictionaryapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private var word: String? = null

    fun onSearch() {
        if (!word.isNullOrEmpty()) {
            viewModelScope.launch {
                try {
                    val resp =  repository.getWord(word!!)
                }catch (e:Exception){
                    Log.d("fetch","exception is ${e.message}")
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