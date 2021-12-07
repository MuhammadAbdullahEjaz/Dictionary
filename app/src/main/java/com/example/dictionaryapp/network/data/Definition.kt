package com.example.dictionaryapp.network.data

data class Definition(
    val definition:String?,
    val example:String?,
    val synonyms:List<String>,
    val antonyms:List<String>
)