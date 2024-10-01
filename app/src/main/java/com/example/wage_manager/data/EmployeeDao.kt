package com.example.wage_manager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.OnConflictStrategy

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    fun getAllEmployees(): LiveData<List<EmployeeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: EmployeeEntity)

    @Update
    suspend fun updateEmployee(employee: EmployeeEntity)

    @Query("DELETE FROM employees WHERE id = :id")
    suspend fun deleteEmployee(id: Int)

    @Query("SELECT * FROM employees WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchEmployees(searchQuery: String): LiveData<List<EmployeeEntity>>
}
