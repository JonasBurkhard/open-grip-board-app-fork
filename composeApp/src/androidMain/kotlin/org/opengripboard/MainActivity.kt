package org.opengripboard

import android.Manifest
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import org.opengripboard.data.AndroidLocalStorageService
import org.opengripboard.data.LocalStorageService
import org.opengripboard.data.objects.Training
import org.opengripboard.model.OgbViewModel
import org.opengripboard.ui.App

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: OgbViewModel
    private val cameraPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            viewModel.onCameraPermissionResult(granted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val localStorageService = AndroidLocalStorageService(prefs)

        viewModel = OgbViewModel(localStorageService)

        setContent {
            LaunchedEffect(viewModel.hasCameraPermission) {
                if (!viewModel.hasCameraPermission) {
                    cameraPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            }
            App(viewModel)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    class FakeLocalStorageService : LocalStorageService {
        private val data = mutableMapOf<String, Training>()
        override fun saveTraining(training: Training) {
            TODO("Not yet implemented")
        }

        override fun loadTraining(id: String): Training? {
            return data[id]
        }
    }

    val fakeStorageService = FakeLocalStorageService()
    App(OgbViewModel(fakeStorageService))
}