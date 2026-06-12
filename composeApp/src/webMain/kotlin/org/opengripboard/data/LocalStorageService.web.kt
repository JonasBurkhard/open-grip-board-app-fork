package org.opengripboard.data

import org.opengripboard.data.objects.Training

class WebLocalStorageService : LocalStorageService {
    override fun saveTraining(training: Training) {

    }

    override fun loadTraining(id: Int): Training? {
        return null
    }
}