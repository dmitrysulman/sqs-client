package io.github.dmitrysulman.sqsclient

import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SqsClientApplication {
    @Bean
    fun sqsMessagingMessageConverter(): SqsMessagingMessageConverter {
        val sqsMessagingMessageConverter = SqsMessagingMessageConverter()
        sqsMessagingMessageConverter.doNotSendPayloadTypeHeader()
        return sqsMessagingMessageConverter
    }
}

fun main(args: Array<String>) {
    runApplication<SqsClientApplication>(*args)
}
