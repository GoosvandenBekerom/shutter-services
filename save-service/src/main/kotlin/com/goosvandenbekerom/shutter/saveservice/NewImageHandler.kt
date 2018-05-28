package com.goosvandenbekerom.shutter.saveservice

import com.goosvandenbekerom.shutter.saveservice.config.KafkaConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NewImageHandler {
    @KafkaListener(topics = [KafkaConfig.TOPIC_NEW_IMAGE], containerFactory = "kafkaListenerContainerFactory")
    fun newImage(data: ConsumerRecord<String, ByteArray>) {
        println(data)
    }
}