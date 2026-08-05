package org.opengripboard.data.objects

import kotlinx.serialization.Serializable
import opengripboard.shared.generated.resources.Res
import opengripboard.shared.generated.resources.left
import opengripboard.shared.generated.resources.right
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Serializable
enum class Side(val display: StringResource) {
    Left(Res.string.left),
    Right(Res.string.right),
}