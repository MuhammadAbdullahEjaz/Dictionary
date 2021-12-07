package com.example.dictionaryapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionaryapp.database.dao.DefinitionDao
import com.example.dictionaryapp.database.dao.MeaningDao
import com.example.dictionaryapp.database.dao.WordDao
import com.example.dictionaryapp.database.models.Definition
import com.example.dictionaryapp.database.models.Meaning
import com.example.dictionaryapp.database.models.Word

@Database(entities = [Word::class, Meaning::class, Definition::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao():WordDao
    abstract fun definitionDao():DefinitionDao
    abstract fun meaningDao():MeaningDao

    companion object{
        private var INSTANCE:AppDatabase? = null

        fun getDatabase(context: Context):AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dictionary_database1"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}