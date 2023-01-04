package com.fourofour.home.sensor.stats.ingester.integration.http.blebox.service

import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

class BleboxStatsProvider(
    val client: WebClient,
    val requestTimeout: Duration,
)