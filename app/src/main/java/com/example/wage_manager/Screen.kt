package com.example.wage_manager

sealed class Screen(val route: String) {
    data object MainScreen : Screen("main_screen")
    data object EmployeeDetails : Screen("employee_details")
}