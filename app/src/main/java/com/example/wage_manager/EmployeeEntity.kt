package com.example.wage_manager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imgUrl: String?,
    val name: String,
    val phoneNumber: String,
    val workingDays: Int = 0,
    val totalSalary: Int = 0,
    val amountPaid: Int = 0,
    val remainingAmount: Int = 0
)