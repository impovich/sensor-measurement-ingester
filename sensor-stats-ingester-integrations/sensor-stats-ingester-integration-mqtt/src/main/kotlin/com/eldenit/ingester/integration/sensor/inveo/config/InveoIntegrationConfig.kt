package com.eldenit.ingester.integration.sensor.inveo.config

import org.reactivestreams.Publisher
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.messaging.Message
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.math.BigDecimal
import java.time.Instant


@Configuration
class InveoIntegrationConfig {

    @Configuration
    @ConditionalOnProperty(prefix = PREFIX, name = ["enabled"], havingValue = "true")
    @EnableConfigurationProperties(InveoMqttProperties::class)
    class Default {

        companion object {
            private const val CHANNEL_NAME = "inveo-mqtt"
        }

        @Bean
        fun inveoMqttClientFactory(properties: InveoMqttProperties): MqttPahoClientFactory =
            DefaultMqttPahoClientFactory().apply { connectionOptions = properties.connection }

        @Bean
        fun inveoMqttEndpoint(
            inveoMqttClientFactory: MqttPahoClientFactory, properties: InveoMqttProperties
        ): MqttPahoMessageDrivenChannelAdapter =
            MqttPahoMessageDrivenChannelAdapter(
                properties.clientId,
                inveoMqttClientFactory,
                *properties.topics.toTypedArray()
            )

        @Bean
        fun inveoMqttInbound(inveoMqttEndpoint: MqttPahoMessageDrivenChannelAdapter): Publisher<Message<BigDecimal>> =
            IntegrationFlows.from(inveoMqttEndpoint)
                .convert(BigDecimal::class.java)
                .channel(CHANNEL_NAME)
                .toReactivePublisher()

        @Bean
        fun inveoStatsPublisher(
            inveoMqttInbound: Publisher<Message<BigDecimal>>,
            properties: InveoMqttProperties
        ): Disposable =
            inveoMqttInbound.toFlux().doOnNext {
                println(it.payload)
                println(it.headers)
                println(Instant.ofEpochMilli(it.headers.timestamp!!))
            }.map { it.payload }.subscribe()
    }

    @Configuration
    @ConditionalOnProperty(prefix = PREFIX, name = ["enabled"], havingValue = "false")
    class NoOp {

        @Bean
        fun inveoStatsPublisher(): Flux<String> = Flux.empty()
    }
}