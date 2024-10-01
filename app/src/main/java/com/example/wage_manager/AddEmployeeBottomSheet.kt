package com.example.wage_manager

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import com.example.wage_manager.data.EmployeeEntity
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEmployeeBottomSheet(
    viewModel: EmployeeViewModel,
    onDismiss: () -> Unit,
    onEmployeeAdded: () -> Unit,
    sheetHeight: Dp
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showImagePickerDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val (takePhoto, pickFromGallery) = rememberImagePicker { uri ->
        imageUri = uri
        showImagePickerDialog = false
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true,
            confirmValueChange = { it != SheetValue.PartiallyExpanded })
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetHeight)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add New Employee",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Image picker placeholder
            Box(modifier = Modifier
                .size(120.dp)
                .clickable { showImagePickerDialog = true }) {
                Log.d("AddEmployeeBottomSheet", "Image URI1: $imageUri")
                if (imageUri == null) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Add photo",
                        modifier = Modifier.size(120.dp)
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Employee photo",
                        modifier = Modifier.size(120.dp),
                        contentScale = ContentScale.Crop
                    )
                    Log.d("AddEmployeeBottomSheet", "Image URI2: $imageUri")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = phone,
                onValueChange = {
                    if (it.length <= 11 && it.all { char -> char.isDigit() }) {
                        phone = it
                    }
                },
                label = { Text("Phone") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Button(
                onClick = {
                    scope.launch {
                        if (imageUri != null) {
                            val imagePath =
                                viewModel.saveImageToInternalStorage(context, imageUri!!)
                            if (imagePath != null) {
                                val currentDate = Calendar.getInstance().time
                                val newEmployee = EmployeeEntity(
                                    id = 0, // Room will auto-generate the ID
                                    imgPath = imagePath,
                                    name = name,
                                    phoneNumber = phone,
                                    workingDays = 1,
                                    totalSalary = 250,
                                    amountPaid = 0,
                                    remainingAmount = 0,
                                    workingDates = listOf(currentDate)
                                )
                                viewModel.insertEmployee(newEmployee)
                                onEmployeeAdded()
                                onDismiss()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Unable to save image",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please select an image",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Employee")
            }
        }
    }

    if (showImagePickerDialog) {
        AlertDialog(
            onDismissRequest = { showImagePickerDialog = false },
            title = { Text("Select Image") },
            text = { Text("Choose a method to add an image") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showImagePickerDialog = false
                        takePhoto()
                    }
                ) {
                    Text("Camera")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showImagePickerDialog = false
                        pickFromGallery()
                    }
                ) {
                    Text("Gallery")
                }
            }
        )
    }
}