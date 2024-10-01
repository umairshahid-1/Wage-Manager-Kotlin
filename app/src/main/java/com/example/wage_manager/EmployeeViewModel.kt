package com.example.wage_manager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wage_manager.data.EmployeeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import com.example.wage_manager.data.EmployeeRepository
import java.io.IOException

class EmployeeViewModel(private val repository: EmployeeRepository) : ViewModel() {
    val employeeList: LiveData<List<EmployeeEntity>> = repository.allEmployees

    fun insertEmployee(employee: EmployeeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertEmployee(employee)
        }
    }

    fun updateEmployee(employee: EmployeeEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateEmployee(employee)
        }
    }

    fun deleteEmployee(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEmployee(id)
        }
    }

    fun searchEmployees(query: String): LiveData<List<EmployeeEntity>> {
        return repository.searchEmployees(query)
    }

    fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // Create a file to save the image
            val fileName = "employee_${UUID.randomUUID()}.jpg"
            val file = File(context.filesDir, fileName)

            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
            }

            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}