package com.example.dictionaryapp

import android.app.Application

class MainApplication : Application() {
    val repository by lazy { MainRepository() }
}