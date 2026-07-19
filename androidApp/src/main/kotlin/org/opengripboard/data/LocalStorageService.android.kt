package org.opengripboard.data

import android.content.SharedPreferences
import kotlinx.serialization.json.Json
import org.opengripboard.data.objects.Training
import androidx.core.content.edit
import org.opengripboard.data.objects.Hangboard

class AndroidLocalStorageService(private val prefs: SharedPreferences) : LocalStorageService {
    private val json = Json

    override fun saveTraining(training: Training) {
        val serialized = json.encodeToString(training)
        prefs.edit { putString("training_${training.id}", serialized) }
    }

    override fun loadTraining(id: String): Training? {
        val serialized = prefs.getString("training_${id}", null) ?: return null
        return json.decodeFromString<Training>(serialized)
    }

    override fun loadAllTrainings(): List<Training> {
        return prefs.all
            .filterKeys { it.startsWith("training_") }
            .mapNotNull { (_, value) ->
                (value as? String)?.let {
                    runCatching {
                        json.decodeFromString<Training>(it)
                    }.getOrNull()
                }
            }
    }

    override fun saveHangboard(hangboard: Hangboard) {
        val serialized = json.encodeToString(hangboard)
        prefs.edit { putString("hangboard_${hangboard.hangboardId}", serialized) }
    }

    override fun loadHangboard(id: String): Hangboard? {
        val serialized = prefs.getString("hangboard_${id}", null) ?: return null
        return json.decodeFromString<Hangboard>(serialized)
    }

    override fun loadAllHangboards(): List<Hangboard> {
        return prefs.all
            .filterKeys { it.startsWith("hangboard_") }
            .mapNotNull { (_, value) ->
                (value as? String)?.let {
                    runCatching {
                        json.decodeFromString<Hangboard>(it)
                    }.getOrNull()
                }
            }
    }

}