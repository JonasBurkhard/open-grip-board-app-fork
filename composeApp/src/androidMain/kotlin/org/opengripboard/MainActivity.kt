package org.opengripboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.opengripboard.model.OgbViewModel
import org.opengripboard.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(OgbViewModel())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(OgbViewModel())
}