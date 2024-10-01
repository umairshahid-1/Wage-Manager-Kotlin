package com.example.wage_manager.data

import androidx.lifecycle.LiveData

class EmployeeRepository(private val employeeDao: EmployeeDao) {
    val allEmployees: LiveData<List<EmployeeEntity>> = employeeDao.getAllEmployees()

    suspend fun insertEmployee(employee: EmployeeEntity) {
        employeeDao.insertEmployee(employee)
    }

    suspend fun updateEmployee(employee: EmployeeEntity) {
        employeeDao.updateEmployee(employee)
    }

    suspend fun deleteEmployee(id: Int) {
        employeeDao.deleteEmployee(id)
    }

    fun searchEmployees(query: String): LiveData<List<EmployeeEntity>> {
        return employeeDao.searchEmployees("%$query%")
    }
}