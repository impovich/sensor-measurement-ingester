package com.fourofour.home.sensor.stats.ingester.integration.http.blebox.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(BleBoxProperties::class)
class BleboxAirIntegrationConfig {

    @Bean
    fun bleboxAirWebClient(properties: BleBoxProperties): WebClient = WebClient.builder().build()
}