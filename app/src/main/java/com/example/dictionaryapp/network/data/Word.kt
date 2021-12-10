package com.example.dictionaryapp.network.data

data class Word(
    val word:String,
    val phonetic:String?,
    val origin:String?,
    val meanings:List<Meaning>
)