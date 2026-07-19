package org.opengripboard.data

interface MqttService {
    fun connectAndSubscribe(
        topic: String,
        onNewMessage: (String) -> Unit = {},
        onConnectionFailed: () -> Unit = {},
    )
}