package org.opengripboard

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.opengripboard.data.AndroidLocalStorageService
import org.opengripboard.data.LocalStorageService
import org.opengripboard.data.objects.Training
import org.opengripboard.model.OgbViewModel
import org.opengripboard.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val localStorageService = AndroidLocalStorageService(prefs)

        setContent {
            val viewModel = OgbViewModel(localStorageService)
            App(viewModel)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    class FakeLocalStorageService : LocalStorageService {
        private val data = mutableMapOf<Int, Training>()

        override fun saveTraining(training: Training) {
            TODO("Not yet implemented")
        }

        override fun loadTraining(id: Int): Training? {
            return data[id]
        }
    }
    val fakeStorageService = FakeLocalStorageService()
    App(OgbViewModel(fakeStorageService))
}