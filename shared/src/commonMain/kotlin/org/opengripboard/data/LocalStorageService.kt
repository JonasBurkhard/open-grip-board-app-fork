package org.opengripboard.data

import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.Training

interface LocalStorageService {
    fun saveTraining(training: Training)
    fun loadTraining(id: String): Training?
    fun deleteTraining(id: String)
    fun loadAllTrainings(): List<Training>
    fun saveHangboard(hangboard: Hangboard)
    fun loadHangboard(id: String): Hangboard?
    fun loadAllHangboards(): List<Hangboard>
}
