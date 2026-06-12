package org.opengripboard.data

import org.opengripboard.data.objects.Training

interface LocalStorageService {
    fun saveTraining(training: Training)
    fun loadTraining(id: Int) : Training?
}
