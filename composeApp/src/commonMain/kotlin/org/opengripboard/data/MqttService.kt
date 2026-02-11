package org.opengripboard.data

expect class MqttService {
    fun connectAndSubscribe(
        topic: String,
        onNewMessage: (String) -> Unit = {},
        onConnectionFailed: () -> Unit = {},
    )
}