package io.github.dmitrysulman.sqsclient.controller

import io.awspring.cloud.sqs.operations.SqsReceiveOptions
import io.awspring.cloud.sqs.operations.SqsTemplate
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.future.await
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.MessageHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.function.Consumer
import kotlin.jvm.optionals.getOrNull

@RestController("/")
class ApiController(
    @Autowired private val sqsTemplate: SqsTemplate
) {
    private val log = KotlinLogging.logger {}

    @PostMapping("/send_message")
    suspend fun sendMessage(
        @RequestParam queue: String,
        @RequestBody message: Map<String, Any>,
    ): UUID {
        val result = sqsTemplate.sendAsync(queue, message).await()
        val messageId = result.messageId
        log.info { "Sent message [$messageId]" }
        return messageId
    }

    @Suppress("UNCHECKED_CAST")
    @GetMapping("/receive_message")
    suspend fun receiveMessage(
        @RequestParam queue: String,
    ): Map<UUID, Map<String, Any>>? {
        val from = Consumer<SqsReceiveOptions> { it.queue(queue) }
        val message = sqsTemplate.receiveAsync(from, Map::class.java).await()

        return message.getOrNull()?.let {
            val messageId = it.headers.get(MessageHeaders.ID) as UUID
            val messagePayload = it.payload as Map<String, Any>
            log.info { "Received message [$messageId]" }
            mapOf(messageId to messagePayload)
        } ?: run {
            log.info { "No messages received" }
            null
        }
    }
}