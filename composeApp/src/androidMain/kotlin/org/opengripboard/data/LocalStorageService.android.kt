package org.opengripboard.data

import android.content.SharedPreferences
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.opengripboard.data.objects.Training
import androidx.core.content.edit

class AndroidLocalStorageService(private val prefs: SharedPreferences): LocalStorageService{
    private val json = Json

    override fun saveTraining(training: Training) {
        val serialized = json.encodeToString(training)
        prefs.edit { putString("training_${training.id}", serialized) }
    }

    override fun loadTraining(id: Int): Training? {
        val serialized = prefs.getString("training_${id}", null) ?: return null
        return json.decodeFromString<Training>(serialized)
    }
}