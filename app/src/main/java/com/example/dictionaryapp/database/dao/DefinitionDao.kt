package com.example.dictionaryapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.dictionaryapp.database.models.Definition

@Dao
interface DefinitionDao {
   @Query("SELECT * FROM definition WHERE meaning_id=:id")
   suspend fun getDefinitionByMeaningId(id:Long):Definition

   @Insert(onConflict = IGNORE)
   suspend fun putDefinition(definition: Definition)
}