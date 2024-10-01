package com.example.wage_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wage_manager.data.EmployeeRepository
import com.example.wage_manager.ui.theme.WageManagerTheme
import kotlinx.coroutines.DelicateCoroutinesApi

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = MainApplication.appDatabase.employeeDao()
        val repository = EmployeeRepository(dao)
        val factory = EmployeeViewModelFactory(repository)
        val employeeViewModel = ViewModelProvider(this, factory)[EmployeeViewModel::class.java]

        setContent {
            WageManagerTheme {
                val navController = rememberNavController()
                AppNavigation(viewModel = employeeViewModel, navController = navController)
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: EmployeeViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {

        // Main Screen
        composable(Screen.MainScreen.route) {
            MainScreen(viewModel, navController)
        }

//        // Employee Details Screen
//        composable(Screen.EmployeeDetails.route) {
//            EmployeeDetailsScreen(viewModel, navController)
//        }
    }
}