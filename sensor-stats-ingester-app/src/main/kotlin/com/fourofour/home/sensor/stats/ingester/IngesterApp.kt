package com.fourofour.home.sensor.stats.ingester

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.concurrent.CountDownLatch

@SpringBootApplication
class IngesterApp

fun main(args: Array<String>) {
    runApplication<IngesterApp>(*args)

    val latch = CountDownLatch(1)
    Runtime.getRuntime().addShutdownHook(Thread { latch.countDown() })
    latch.await()
}