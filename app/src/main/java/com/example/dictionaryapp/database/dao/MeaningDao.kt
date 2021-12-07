package com.example.dictionaryapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.dictionaryapp.database.models.Meaning

@Dao
interface MeaningDao {
    @Query("SELECT * FROM meaning WHERE word_id=:id")
    suspend fun getMeaningByWordId(id:Long):List<Meaning>

    @Insert(onConflict = IGNORE)
    suspend fun putMeaning(meaning: Meaning):Long?
}