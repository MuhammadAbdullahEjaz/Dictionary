package com.example.dictionaryapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.dictionaryapp.database.models.Word

@Dao
interface WordDao {
    @Query("SELECT w.word FROM word as w")
    suspend fun getAllWords(): List<String>

    @Query("SELECT * FROM word as w WHERE w.word=:text")
    suspend fun getWord(text:String):Word?

    @Query("SELECT w.id FROM word as w WHERE w.word=:text")
    suspend fun getWordId(text: String):Long?

    @Insert(onConflict = IGNORE)
    suspend fun putWord(word: Word):Long?
}