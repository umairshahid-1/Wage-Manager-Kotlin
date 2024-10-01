package com.example.wage_manager

import android.app.Application
import androidx.room.Room
import com.example.wage_manager.data.AppDatabase

class MainApplication : Application() {

    companion object {
        @Volatile
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        synchronized(this) {
            appDatabase = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
    }
}