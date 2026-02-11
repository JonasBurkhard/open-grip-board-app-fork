package org.opengripboard.model

import androidx.compose.runtime.mutableStateListOf
import org.opengripboard.data.objects.Training

class TrainingsManager {
    var pastTrainings = mutableStateListOf<Training>()
        private set

    fun onDeletePressed(id: Int) {
        pastTrainings.removeAll { training -> training.id == id }
    }

    fun addTraining(newTraining: Training) {
        pastTrainings.add(newTraining)
    }

    fun addTrainings(newTrainings: List<Training>) {
        newTrainings.forEach { training -> pastTrainings.add(training) }
    }
}