package org.opengripboard

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import org.opengripboard.data.LocalStorageService
import org.opengripboard.data.objects.Training
import org.opengripboard.model.OgbViewModel
import org.opengripboard.ui.App
import androidx.activity.addCallback
import org.opengripboard.data.objects.Hangboard
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import org.org.opengripboard.data.AndroidLocalStorageService
import org.opengripboard.model.createDataStore
import okio.Path.Companion.toPath

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
        val dataStore = createDataStore(applicationContext)

        viewModel = OgbViewModel(localStorageService, dataStore)

        onBackPressedDispatcher.addCallback(this) {
            viewModel.navigation.navigateBack()
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true

        setContent {
            LaunchedEffect(viewModel.shouldRequestCameraPermission) {
                if (viewModel.shouldRequestCameraPermission) {
                    cameraPermissionLauncher.launch(
                        Manifest.permission.CAMERA
                    )
                }
            }
            LaunchedEffect(viewModel.openSettingsEvent) {
                if (viewModel.openSettingsEvent) {
                    openAppSettings()
                    viewModel.onOpenSettingsHandled()
                }
            }
            App(viewModel)
        }
    }

    fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        startActivity(intent)
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

        override fun loadAllTrainings(): List<Training> {
            TODO("Not yet implemented")
        }

        override fun saveHangboard(hangboard: Hangboard) {
            TODO("Not yet implemented")
        }

        override fun loadHangboard(id: String): Hangboard {
            TODO("Not yet implemented")
        }

        override fun loadAllHangboards(): List<Hangboard> {
            TODO("Not yet implemented")
        }
    }

    val fakeStorageService = FakeLocalStorageService()
    val previewDataStore = PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            "/tmp/preview.preferences_pb".toPath()
        }
    )
    App(OgbViewModel(fakeStorageService, previewDataStore))
}