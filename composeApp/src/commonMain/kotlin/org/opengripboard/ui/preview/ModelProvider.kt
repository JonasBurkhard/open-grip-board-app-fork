package org.opengripboard.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.opengripboard.model.OgbViewModel
import org.opengripboard.model.PageId
import kotlin.apply

class ModelProvider : PreviewParameterProvider<OgbViewModel> {
    override val values: Sequence<OgbViewModel> = sequenceOf(
        OgbViewModel().apply{
            
        },
        OgbViewModel().apply{
            navigation.navigate(PageId.PastTrainings)
        },
    )
}