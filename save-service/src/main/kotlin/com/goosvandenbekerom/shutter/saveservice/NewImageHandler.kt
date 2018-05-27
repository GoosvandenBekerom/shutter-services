package com.goosvandenbekerom.shutter.saveservice

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NewImageHandler {
    @KafkaListener(topics = ["image"])
    fun newImage(data: Any) {
        println(data)
    }
}