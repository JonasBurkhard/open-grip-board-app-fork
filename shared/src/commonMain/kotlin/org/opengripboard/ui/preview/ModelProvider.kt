package org.opengripboard.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.opengripboard.data.MqttService
import org.opengripboard.data.SettingsRepository
import org.opengripboard.data.objects.GripType
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.HangboardStatus
import org.opengripboard.data.objects.Side
import org.opengripboard.data.objects.Training
import org.opengripboard.model.OgbViewModel
import org.opengripboard.model.PageId
import kotlin.apply
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class ModelProvider : PreviewParameterProvider<OgbViewModel> {

    class PreviewSettingsRepository : SettingsRepository {
        override var language = "en"
    }

    class PreviewMqttService : MqttService {
        override fun connectAndSubscribe(
            topic: String,
            onNewMessage: (String) -> Unit,
            onConnectionFailed: () -> Unit,
        ) {
        }
    }

    override val values: Sequence<OgbViewModel> = sequenceOf(
        OgbViewModel(
            PreviewLocalStorageService(),
            PreviewSettingsRepository(),
            PreviewMqttService()
        ).apply {
        },
        OgbViewModel(
            PreviewLocalStorageService(),
            PreviewSettingsRepository(),
            PreviewMqttService()
        ).apply {
            trainings.addTrainings(
                List(28) { id ->
                    Training(
                        id = id.toString(),
                        date = Clock.System.now().minus(Random.nextInt(0, 15).days)
                            .toLocalDateTime(TimeZone.currentSystemDefault()),
                        dataPoints = List(20) { Random.nextInt(5000, 60000) },
                        duration = Random.nextInt(1, 120).minutes,
                        gripType = GripType("fullHand", Side.Right)
                    )
                }
            )
            navigation.navigate(PageId.PastTrainings)
            statistics.recalculateFor(PageId.Dashboard, trainings.pastTrainings)
            hangboards.addHangboards(
                List(3) { id ->
                    Hangboard(id.toString(), id.toString(), HangboardStatus.Online)
                }
            )
        },
        OgbViewModel(
            PreviewLocalStorageService(),
            PreviewSettingsRepository(),
            PreviewMqttService()
        ).apply {
            trainings.addTrainings(
                List(28) { id ->
                    Training(
                        id = id.toString(),
                        date = Clock.System.now().minus(Random.nextInt(0, 15).days)
                            .toLocalDateTime(TimeZone.currentSystemDefault()),
                        dataPoints = List(20) { Random.nextInt(5000, 60000) },
                        duration = Random.nextInt(1, 120).minutes,
                        gripType = GripType("fullHand", Side.Right)
                    )
                }
            )
            navigation.navigate(PageId.Dashboard)
            statistics.recalculateFor(PageId.Dashboard, trainings.pastTrainings)
            trainings.onStartRecordingTraining()
            List(20) { hangboards.currentReadings.add(Random.nextInt(5000, 60000) )}
        },
    )
}