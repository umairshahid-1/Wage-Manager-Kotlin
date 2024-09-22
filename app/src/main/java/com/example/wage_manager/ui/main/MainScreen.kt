package com.example.wage_manager.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wage_manager.ui.navigation.Screen

data class Employee(
    val name: String,
    val workingDays: Int,
    val totalSalary: Int,
    val paidAmount: Int,
    val remainingAmount: Int
)

@Composable
fun CustomTopBar(
    onSearchClick: () -> Unit,
    title: String,
    onAddClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Employee",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Employee",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun CustomBottomBar(
    onChangeClick: () -> Unit,
    onAddClick: () -> Unit,
    onExportClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        BottomAppBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onChangeClick) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Change"
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onExportClick) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Export/Download"
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = onAddClick,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-20).dp) // Adjust this value to position FAB
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    // Dummy data for now
    val employees = listOf(
        Employee("Umair", 2, 500, 250, 250),
        Employee("Umair", 2, 500, 250, 250),
        Employee("Umair", 2, 500, 250, 250),
        Employee("Umair", 2, 500, 250, 250)
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                onSearchClick = {},
                title = "Wage Manager",
                onAddClick = {
                    // Code to change working day price screen
                }
            )
        },
        bottomBar = {
            CustomBottomBar(
                onChangeClick = { /* Handle change action */ },
                onAddClick = { /* Handle add action */ },
                onExportClick = { /* Handle export action */ }
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            EmployeeList(employees = employees, navController = navController)
        }
    }
}

@Composable
fun EmployeeList(employees: List<Employee>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(employees) { employee ->
            EmployeeItem(employee, navController)
        }
    }
}

@Composable
fun EmployeeItem(employee: Employee, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Add Pic",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = employee.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${employee.workingDays}")
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${employee.totalSalary} Rs")
                Text(text = "${employee.remainingAmount} Rs")
            }
        }
//        // OnClick event for each employee
//        Button(
//            onClick = { navController.navigate(Screen.EmployeeDetails.route) },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("Details")
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    // Use rememberNavController for previews
    val navController = rememberNavController()
    MainScreen(navController = navController)
}