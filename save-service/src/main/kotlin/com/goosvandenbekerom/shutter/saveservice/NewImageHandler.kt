package com.goosvandenbekerom.shutter.saveservice

import com.goosvandenbekerom.shutter.saveservice.config.KafkaConfig
import com.goosvandenbekerom.shutter.saveservice.services.FtpService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NewImageHandler(private val ftp: FtpService) {
    @KafkaListener(topics = [KafkaConfig.TOPIC_NEW_IMAGE], containerFactory = "kafkaListenerContainerFactory")
    fun newImage(data: ConsumerRecord<String, ByteArray>) {
        val id = data.key()
        println("received image with id $id")

        if (ftp.upload(id, data.value()))
            println("upload of image $id successful")
        else
            println("upload of image $id failed") // TODO: upload error to some kind of logging topic?
    }
}