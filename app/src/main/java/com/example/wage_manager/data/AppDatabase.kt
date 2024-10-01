package com.example.wage_manager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [EmployeeEntity::class], version = 1)
@TypeConverters(Convertors::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val NAME = "EmployeeDB"
    }

    abstract fun employeeDao(): EmployeeDao
}