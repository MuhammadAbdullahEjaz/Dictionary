package com.example.dictionaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.dictionaryapp.databinding.ActivityWordsBinding

class WordsActivity : AppCompatActivity() {

    val viewModel:WordViewModel by viewModels{WordViewModelFactory((this.application as MainApplication).repository)}
    lateinit var binding: ActivityWordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        viewModel.getAllWords()
        binding.viewModel = viewModel

    }
}