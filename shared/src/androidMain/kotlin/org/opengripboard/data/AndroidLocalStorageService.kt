package org.opengripboard.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import org.opengripboard.data.objects.Training
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.HangboardStatus
import org.opengripboard.database.AppDatabase
import kotlin.time.Duration.Companion.milliseconds

class AndroidLocalStorageService(
    database: AppDatabase
) : LocalStorageService {

    private val trainingQueries = database.trainingQueries
    private val hangboardQueries = database.hangboardQueries

    override fun saveTraining(training: Training) {
        trainingQueries.insert(
            training.id,
            training.date.toString(),
            Json.encodeToString(training.dataPoints),
            training.duration.inWholeMilliseconds
        )
    }

    override fun loadTraining(id: String): Training? =
        trainingQueries.selectById(id)
            .executeAsOneOrNull()
            ?.let {
                Training(
                    it.id,
                    LocalDateTime.parse(it.date),
                    Json.decodeFromString(it.dataPoints),
                    it.duration.milliseconds
                )
            }

    override fun loadAllTrainings(): List<Training> =
        trainingQueries.selectAll()
            .executeAsList()
            .map {
                Training(
                    it.id,
                    LocalDateTime.parse(it.date),
                    Json.decodeFromString(it.dataPoints),
                    it.duration.milliseconds
                )
            }

    override fun saveHangboard(hangboard: Hangboard) {
        hangboardQueries.insert(
            hangboard.hangboardId,
            hangboard.name,
            hangboard.status.name
        )
    }

    override fun loadHangboard(id: String): Hangboard? =
        hangboardQueries.selectById(id)
            .executeAsOneOrNull()
            ?.let {
                Hangboard(
                    it.name,
                    it.hangboardId,
                    HangboardStatus.valueOf(it.status)
                )
            }

    override fun loadAllHangboards(): List<Hangboard> =
        hangboardQueries.selectAll()
            .executeAsList()
            .map {
                Hangboard(
                    it.name,
                    it.hangboardId,
                    HangboardStatus.valueOf(it.status)
                )
            }
}