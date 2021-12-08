package com.example.dictionaryapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.dictionaryapp.database.models.SynonymsAntonyms
import com.example.dictionaryapp.database.models.TYPE

@Dao
interface SynonymsAntonymsDao {
    @Query("SELECT entry FROM synonymsantonyms WHERE meaning_id=:id AND type=:t")
    suspend fun getSynonyms(id:Long, t: TYPE =TYPE.SYNONYMS):List<String>?

    @Query("SELECT entry FROM synonymsantonyms WHERE meaning_id=:id AND type=:t")
    suspend fun getAntonyms(id:Long, t: TYPE =TYPE.ANTONYMS):List<String>?

    @Insert(onConflict = IGNORE)
    suspend fun putEntry(synonymsAntonyms: SynonymsAntonyms)


}