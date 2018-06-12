package com.goosvandenbekerom.shutter.saveservice

import com.goosvandenbekerom.shutter.saveservice.config.KafkaConfig
import com.goosvandenbekerom.shutter.saveservice.services.FtpService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@Component
class NewImageHandler(private val ftp: FtpService) {
    @KafkaListener(topics = [KafkaConfig.TOPIC_NEW_IMAGE], containerFactory = "kafkaListenerContainerFactory")
    fun newImage(image: ByteArray, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) id: String) {
        println("received image with id $id")

        if (ftp.upload(id, image))
            println("upload of image $id successful")
        else
            println("upload of image $id failed") // TODO: upload error to some kind of logging topic?
    }
}