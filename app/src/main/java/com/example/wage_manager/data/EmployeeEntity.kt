package com.example.wage_manager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imgPath: String?,
    val name: String,
    val phoneNumber: String,
    val workingDays: Int = 0,
    val totalSalary: Int = 0,
    val amountPaid: Int = 0,
    val remainingAmount: Int = 0,
    @ColumnInfo(name = "workingDates") val workingDates: List<Date> = emptyList()
)