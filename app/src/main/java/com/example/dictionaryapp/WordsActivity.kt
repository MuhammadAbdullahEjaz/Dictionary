package com.example.dictionaryapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.dictionaryapp.databinding.ActivityWordsBinding
import com.example.dictionaryapp.utils.OnItemClickListener

class WordsActivity : AppCompatActivity() , OnItemClickListener {

    val viewModel:WordViewModel by viewModels{WordViewModelFactory((this.application as MainApplication).repository)}
    lateinit var binding: ActivityWordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        viewModel.getAllWords()
        binding.viewModel = viewModel
        binding.clickListener = this

        binding.home.setOnClickListener{
            finish()
        }
        binding.deleteAll.setOnClickListener{
            val dialog = AlertDialog.Builder(this)
            dialog.setMessage("Delete all history?")
            dialog.apply {
                setPositiveButton("YES", DialogInterface.OnClickListener{
                    dialog, id->
                    viewModel.deleteAllWords()
                    binding.allWords.visibility = View.GONE
                })
                setNegativeButton("NO", DialogInterface.OnClickListener{
                    dialog, id->
                })
            }
            dialog.create()
            dialog.show()
        }
    }
    override fun onClick(word:String) {
        val data = Intent()
        data.putExtra("WORD",word)
        setResult(RESULT_OK, data)
        finish()
    }
}