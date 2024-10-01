package com.example.wage_manager

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImagePickerUtils(private val context: Context) {
    private var tempImageUri: Uri? = null

    fun createImageFile(): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            context.externalCacheDir
        )
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            image
        ).also { tempImageUri = it }
    }

    fun getTempImageUri(): Uri? = tempImageUri
}

@Composable
fun rememberImagePicker(
    onImagePicked: (Uri) -> Unit
): Pair<() -> Unit, () -> Unit> {
    val context = LocalContext.current
    val imagePickerUtils = remember { ImagePickerUtils(context) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                imagePickerUtils.getTempImageUri()?.let { onImagePicked(it) }
            }
        }
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { onImagePicked(it) }
        }
    )

    val takePhoto = {
        val uri = imagePickerUtils.createImageFile()
        cameraLauncher.launch(uri)
    }

    val pickFromGallery = {
        galleryLauncher.launch("image/*")
    }

    return Pair(takePhoto, pickFromGallery)
}