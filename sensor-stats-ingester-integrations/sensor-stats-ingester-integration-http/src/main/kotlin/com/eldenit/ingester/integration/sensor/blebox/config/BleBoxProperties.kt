package com.eldenit.ingester.integration.sensor.blebox.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

internal const val PREFIX = "ingester.source.http.blebox-air"

@ConfigurationProperties(PREFIX)
@ConstructorBinding
data class BleBoxProperties(
    val enabled: Boolean,
    val urls: List<String> = mutableListOf(),
    val requestTimeout: Duration = Duration.ofSeconds(1)
)