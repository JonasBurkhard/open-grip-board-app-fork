package org.opengripboard

import android.Manifest
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
import org.opengripboard.data.AndroidDatabaseDriverFactory
import org.opengripboard.data.AndroidLocalStorageService
import org.opengripboard.data.AndroidMqttService
import org.opengripboard.data.Database
import org.opengripboard.data.MqttService
import org.opengripboard.data.SettingsRepository
import org.opengripboard.di.AppDependencies
import org.opengripboard.ui.customAppLocale

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

        val database = Database(
            AndroidDatabaseDriverFactory(applicationContext)
        )
        val localStorageService = AndroidLocalStorageService(database.db)

        // Restore persisted language
        customAppLocale = AppDependencies.settingsRepository.language

        viewModel = OgbViewModel(localStorageService, AppDependencies.settingsRepository,
            AndroidMqttService())

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
        private val trainings = mutableMapOf<String, Training>()
        private val hangboards = mutableMapOf<String, Hangboard>()

        override fun saveTraining(training: Training) {
            trainings[training.id] = training
        }

        override fun loadTraining(id: String): Training? {
            return trainings[id]
        }

        override fun loadAllTrainings(): List<Training> {
            return trainings.values.toList()
        }

        override fun deleteTraining(id: String) {
        }

        override fun saveHangboard(hangboard: Hangboard) {
            hangboards[hangboard.hangboardId] = hangboard
        }

        override fun loadHangboard(id: String): Hangboard? {
            return hangboards[id]
        }

        override fun loadAllHangboards(): List<Hangboard> {
            return hangboards.values.toList()
        }
    }

    class PreviewSettingsRepository : SettingsRepository {
        override var language = "en"
    }

    class PreviewMqttService: MqttService{
        override fun connectAndSubscribe(
            topic: String,
            onNewMessage: (String) -> Unit,
            onConnectionFailed: () -> Unit
        ) {
        }

    }
    App(OgbViewModel(FakeLocalStorageService(),PreviewSettingsRepository(), PreviewMqttService()))
}