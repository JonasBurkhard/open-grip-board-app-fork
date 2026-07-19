package org.opengripboard.ui.preview

import org.opengripboard.data.LocalStorageService
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.Training

class PreviewLocalStorageService : LocalStorageService {

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