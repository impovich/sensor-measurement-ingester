package com.eldenit.ingester.integration.sensor.blebox.service

import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

class BleboxStatsProvider(
    val client: WebClient,
    val requestTimeout: Duration,
)