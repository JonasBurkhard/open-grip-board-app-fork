package org.opengripboard.data.objects

import kotlinx.serialization.Serializable

@Serializable
data class GripType(
    val name: String,
    val side: Side,
)
