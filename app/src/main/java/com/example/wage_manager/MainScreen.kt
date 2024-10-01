package com.example.wage_manager

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.wage_manager.data.EmployeeEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.size.Size

@Composable
fun CustomTopBar(
    onSearchClick: () -> Unit, title: String, onAddClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp), color = MaterialTheme.colorScheme.primary
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
    onChangeClick: () -> Unit, onAddClick: () -> Unit, onExportClick: () -> Unit
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
                        imageVector = Icons.Default.Create, contentDescription = "Change"
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onExportClick) {
                    Icon(
                        imageVector = Icons.Default.Done, contentDescription = "Export/Download"
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
fun MainScreen(viewModel: EmployeeViewModel, navController: NavController) {
    val employeesList by viewModel.employeeList.observeAsState(initial = emptyList())
    var showAddEmployeeSheet by remember { mutableStateOf(false) }
    var showConfirmation by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomTopBar(onSearchClick = {}, title = "Wage Manager", onAddClick = {
                // Code to change working day price screen
            })
        },
        bottomBar = {
            CustomBottomBar(onChangeClick = { /* Handle change action */ },
                onAddClick = { showAddEmployeeSheet = true },
                onExportClick = { /* Handle export action */ })
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            EmployeeList(employees = employeesList, navController = navController)
        }
        if (showConfirmation) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        "Employee Added!",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        if (showAddEmployeeSheet) {
            AddEmployeeBottomSheet(
                viewModel = viewModel,
                onDismiss = { showAddEmployeeSheet = false },
                onEmployeeAdded = {
                    showConfirmation = true
                    scope.launch {
                        delay(2000) // Show confirmation for 2 seconds
                        showConfirmation = false
                    }
                },
                sheetHeight = 500.dp // Adjust this value as needed
            )
        }
    }
}

@Composable
fun EmployeeList(employees: List<EmployeeEntity>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(employees) { employee ->
            EmployeeItem(
                employee = employee,
                onItemClick = { clickedEmployee ->
                    // Navigate to employee details screen
                    navController.navigate("employeeDetails/${clickedEmployee.id}")
                }
            )
        }
    }
}

@Composable
fun EmployeeItem(employee: EmployeeEntity, onItemClick: (EmployeeEntity) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(employee) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            ) {
                if (employee.imgPath != null) {
                    var isImageLoading by remember { mutableStateOf(true) }
                    var isImageLoadError by remember { mutableStateOf(false) }

                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(employee.imgPath)
                            .size(Size.ORIGINAL)
                            .listener(
                                onStart = {
                                    isImageLoading = true
                                    isImageLoadError = false
                                },
                                onSuccess = { _, _ ->
                                    isImageLoading = false
                                    isImageLoadError = false
                                },
                                onError = { _, _ ->
                                    isImageLoading = false
                                    isImageLoadError = true
                                }
                            )
                            .build()
                    )

                    if (isImageLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Image(
                        painter = painter,
                        contentDescription = "Employee Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    if (isImageLoadError) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Error Loading Image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "No Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = employee.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${employee.workingDays} days")
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${employee.totalSalary} Rs")
                Text(text = "${employee.remainingAmount} Rs")
            }
        }
    }
}