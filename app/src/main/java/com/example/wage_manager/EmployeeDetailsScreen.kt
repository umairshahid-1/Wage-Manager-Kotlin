//package com.example.wage_manager
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//
//data class WorkingDay(val day: String, val date: String)
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EmployeeDetailsScreen(viewModel: EmployeeViewModel, navController: NavController) {
//    val employeeName = "Umair"
//    val phoneNumber = "03126269027"
//    val workingDays = listOf(
//        WorkingDay("Monday", "08/12/2024"),
//        WorkingDay("Monday", "15/12/2024")
//    )
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Employee Details") }
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = {
//                // Handle adding a new working day
//            }) {
//                Icon(Icons.Default.Add, contentDescription = "Add Working Day")
//            }
//        }
//    ) { padding ->
//        Box(modifier = Modifier.padding(padding)) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                // Employee Details Section
//                Text(text = "Name: $employeeName", style = MaterialTheme.typography.bodyLarge)
//                Text(
//                    text = "Phone Number: $phoneNumber",
//                    style = MaterialTheme.typography.bodyLarge
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Financial Information
//                EmployeeFinancialDetails(totalSalary = 500, paidAmount = 250, remainingAmount = 250)
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Working Days List
//                Text(text = "Working Days:", style = MaterialTheme.typography.bodyLarge)
//                WorkingDayList(workingDays = workingDays)
//            }
//        }
//    }
//}
//
//@Composable
//fun EmployeeFinancialDetails(totalSalary: Int, paidAmount: Int, remainingAmount: Int) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceEvenly
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(text = "Total Salary")
//            Text(text = "$totalSalary Rs")
//        }
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(text = "Amount Paid")
//            Text(text = "$paidAmount Rs")
//        }
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(text = "Remaining")
//            Text(text = "$remainingAmount Rs")
//        }
//    }
//}
//
//@Composable
//fun WorkingDayList(workingDays: List<WorkingDay>) {
//    LazyColumn(
//        modifier = Modifier.fillMaxWidth(),
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(workingDays) { workingDay ->
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//            ) {
//                Row(
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(text = workingDay.day)
//                    Text(text = workingDay.date)
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun EmployeeDetailsScreenPreview() {
//    // Use rememberNavController for previews
//    val navController = rememberNavController()
//    EmployeeDetailsScreen(navController = navController)
//}