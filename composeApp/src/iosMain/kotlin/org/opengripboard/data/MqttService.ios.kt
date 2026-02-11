package org.opengripboard.data

actual class MqttService {
    actual fun connectAndSubscribe(
        topic: String,
        onNewMessage: (String) -> Unit,
        onConnectionFailed: () -> Unit,
    ) {

    }
}