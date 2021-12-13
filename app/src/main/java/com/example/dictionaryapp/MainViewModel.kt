package com.example.dictionaryapp

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.*
import com.example.dictionaryapp.network.data.Word
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    var words: String? = null

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
            viewModelScope.launch {
                try {
                    val word = repository.getItorSetIt(words!!)
                    _result.postValue(word)
                    _resultVisibility.postValue(true)
                }catch (constraint: SQLiteConstraintException){
                    //do nothing
                }catch (error:Exception){
                    _errorVisibility.postValue(true)
                }
                _loadingVisibility.postValue(false)
            }
        }
    }

    fun dumy(){
        Log.d("fetch", "dumy function")
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