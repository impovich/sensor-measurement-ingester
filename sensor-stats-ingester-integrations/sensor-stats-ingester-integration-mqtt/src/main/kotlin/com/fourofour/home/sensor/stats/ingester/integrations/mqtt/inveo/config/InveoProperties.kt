package com.fourofour.home.sensor.stats.ingester.integrations.mqtt.inveo.config

import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.NestedConfigurationProperty

internal const val PREFIX = "ingester.source.mqtt.inveo"

@ConfigurationProperties(PREFIX)
@ConstructorBinding
data class InveoMqttProperties(
    val enabled: Boolean,
    val clientId: String = "CHANGE_ME",
    val sensorMetaMapping: Map<String, SensorMeta> = mutableMapOf(),
    val topics: MutableList<String> = mutableListOf(),
    @NestedConfigurationProperty val connection: MqttConnectOptions = MqttConnectOptions()
) {
    @ConstructorBinding
    data class SensorMeta(val name: String, val type: String, val location: String)
}