package org.opengripboard.data

import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import java.util.UUID
import kotlin.text.Charsets.UTF_8
import java.nio.charset.StandardCharsets

actual class MqttService actual constructor() {
    private val serverHost: String = "opengripboard.org"
    private val qos: MqttQos = MqttQos.EXACTLY_ONCE
    private val username: String = "android"
    private val password: String = "android"

    private val client = Mqtt5Client.builder()
        .serverHost(serverHost)
        .identifier(UUID.randomUUID().toString())
        // .sslWithDefaultConfig()
        .simpleAuth()
        .username(username)
        .password(UTF_8.encode(password))
        .applySimpleAuth()
        .serverPort(1883)
        .buildAsync()

    actual fun connectAndSubscribe(
        topic: String,
        onNewMessage: (String) -> Unit,
        onConnectionFailed: () -> Unit,
    ) {
        if (client.state.isConnected) {
            subscribe(topic, onNewMessage)
            return
        }
        client.connectWith()
            .cleanStart(true)
            .keepAlive(30)
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    onConnectionFailed()
                } else { //erst wenn die Connection aufgebaut ist, kann subscribed werden
                    subscribe(topic, onNewMessage)
                }
            }
    }

    private fun subscribe(
        topic: String,
        onNewMessage: (String) -> Unit
    ) {
        client.subscribeWith()
            .topicFilter(topic)
            .qos(qos)
            .noLocal(true)
            .callback { onNewMessage(it.payloadAsString()) }
            .send()
    }

    fun publish(
        topic: String,
        message: String,
        onPublished: () -> Unit = {},
        onError: () -> Unit = {}
    ) {
        client.publishWith()
            .topic(topic)
            .payload(message.asPayload())
            .qos(qos)
            .retain(false)  //Message soll nicht auf dem Broker gespeichert werden
            .messageExpiryInterval(120)
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    onError()
                } else {
                    onPublished()
                }
            }
    }

}

// praktische Extension Functions
private fun String.asPayload(): ByteArray = toByteArray(StandardCharsets.UTF_8)
private fun Mqtt5Publish.payloadAsString(): String = String(payloadAsBytes, StandardCharsets.UTF_8)