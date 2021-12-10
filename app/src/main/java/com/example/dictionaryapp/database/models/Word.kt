package com.example.dictionaryapp.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id:Long?,
    val word:String,
    val phonetic:String?,
    val origin:String?
)
