package io.github.dmitrysulman.sqsclient.controller

import io.awspring.cloud.sqs.operations.SqsTemplate
import kotlinx.coroutines.future.await
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController("/")
class ApiController(
    @Autowired private val sqsTemplate: SqsTemplate
) {
    @PostMapping("/send_message")
    suspend fun sendMessage(
        @RequestParam queue: String,
        @RequestBody message: Map<String, Any>
    ): UUID {
        val result = sqsTemplate.sendAsync(queue, message).await()
        return result.messageId
    }
}