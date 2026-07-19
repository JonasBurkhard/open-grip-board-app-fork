package org.opengripboard.data

class IosMqttService : MqttService {
    override fun connectAndSubscribe(
        topic: String,
        onNewMessage: (String) -> Unit,
        onConnectionFailed: () -> Unit,
    ) {

    }
}