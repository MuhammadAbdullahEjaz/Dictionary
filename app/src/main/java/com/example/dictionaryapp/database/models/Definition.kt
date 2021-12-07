package com.example.dictionaryapp.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Meaning::class, parentColumns = ["id"],childColumns = ["meaning_id"], onDelete = CASCADE)])
data class Definition(
    @PrimaryKey(autoGenerate = true) val id:Long?,
    val definition:String?,
    val example:String?,
    val meaning_id:Long
)
