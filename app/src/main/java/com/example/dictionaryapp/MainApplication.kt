package com.example.dictionaryapp

import android.app.Application
import com.example.dictionaryapp.database.AppDatabase

class MainApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { MainRepository(database) }
}