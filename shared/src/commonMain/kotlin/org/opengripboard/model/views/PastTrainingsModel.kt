package org.opengripboard.model.views

import org.opengripboard.data.objects.Training
import org.opengripboard.model.TrainingsManager

class PastTrainingsModel(private val trainingsManager: TrainingsManager) {

    fun onDeleteTraining(training: Training){
        trainingsManager.deleteTraining(training)
    }

}