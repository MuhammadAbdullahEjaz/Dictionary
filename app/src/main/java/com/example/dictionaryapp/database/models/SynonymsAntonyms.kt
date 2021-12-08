package com.example.dictionaryapp.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Meaning::class, parentColumns = ["id"],childColumns = ["meaning_id"], onDelete = ForeignKey.CASCADE)])
data class SynonymsAntonyms (
    @PrimaryKey(autoGenerate = true) val id:Int?,
    val meaning_id:Long,
    val type:TYPE,
    val entry:String
)

enum class TYPE {
    SYNONYMS, ANTONYMS
}