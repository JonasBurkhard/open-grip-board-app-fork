package org.opengripboard.data

class WebMqttService : MqttService {
    override fun connectAndSubscribe(
        topic: String,
        onNewMessage: (String) -> Unit,
        onConnectionFailed: () -> Unit,
    ) {

    }
}