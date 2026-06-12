package org.opengripboard.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.opengripboard.data.LocalStorageService
import org.opengripboard.data.objects.Hangboard
import org.opengripboard.data.objects.HangboardStatus
import org.opengripboard.data.objects.Training
import org.opengripboard.model.OgbViewModel
import org.opengripboard.model.PageId
import kotlin.apply
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class ModelProvider() : PreviewParameterProvider<OgbViewModel> {
    class FakeLocalStorageService : LocalStorageService {
        private val data = mutableMapOf<String, Training>()
        override fun saveTraining(training: Training) {
            TODO("Not yet implemented")
        }
        override fun loadTraining(id: String): Training? {
            return data[id]
        }
    }
    override val values: Sequence<OgbViewModel> = sequenceOf(
        OgbViewModel(FakeLocalStorageService()).apply {

        },
        OgbViewModel(FakeLocalStorageService()).apply {
            trainings.addTrainings(
                List(28) { id ->
                    Training(
                        id = id.toString(),
                        date = Clock.System.now().minus(Random.nextInt(0, 15).days)
                            .toLocalDateTime(TimeZone.currentSystemDefault()),
                        dataPoints = List(20) { Random.nextInt(5000, 60000) },
                        duration = Random.nextInt(1, 120).minutes
                    )
                }
            )
            navigation.navigate(PageId.PastTrainings)
            hangboards.addHangboards(
                List(3) { id ->
                    Hangboard(id.toString(), id, HangboardStatus.Online)
                }
            )
        },
        OgbViewModel(FakeLocalStorageService()).apply {
            trainings.addTrainings(
                List(28) { id ->
                    Training(
                        id = id.toString() ,
                        date = Clock.System.now().minus(Random.nextInt(0, 15).days)
                            .toLocalDateTime(TimeZone.currentSystemDefault()),
                        dataPoints = List(20) { Random.nextInt(5000, 60000) },
                        duration = Random.nextInt(1, 120).minutes
                    )
                }
            )
            navigation.navigate(PageId.Dashboard)
        },
    )
}