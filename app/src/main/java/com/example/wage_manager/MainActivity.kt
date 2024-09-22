package com.example.wage_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wage_manager.ui.main.MainScreen
import com.example.wage_manager.ui.employee.EmployeeDetailsScreen
import com.example.wage_manager.ui.navigation.Screen
import com.example.wage_manager.ui.theme.WageManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WageManagerTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {

        // Main Screen
        composable(Screen.MainScreen.route) {
            MainScreen(navController)
        }

        // Employee Details Screen
        composable(Screen.EmployeeDetails.route) {
            EmployeeDetailsScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WageManagerTheme {
        val navController = rememberNavController()
        AppNavigation(navController)
    }
}