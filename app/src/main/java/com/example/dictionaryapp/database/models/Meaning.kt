package com.example.dictionaryapp.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Word::class, parentColumns = ["id"], childColumns = ["word_id"], onDelete = CASCADE)])
data class Meaning(
    @PrimaryKey(autoGenerate = true) val id:Long?,
    val partOfSpeech:String,
    val word_id:Long
)
