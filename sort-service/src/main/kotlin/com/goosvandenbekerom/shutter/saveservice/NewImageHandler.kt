package com.goosvandenbekerom.shutter.saveservice

import com.goosvandenbekerom.shutter.saveservice.config.KafkaConfig
import com.goosvandenbekerom.shutter.saveservice.repositories.ImageRepository
import com.goosvandenbekerom.shutter.saveservice.services.FtpService
import com.goosvandenbekerom.shutter.saveservice.services.SortingService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NewImageHandler(private val sortingService: SortingService, private val ftp: FtpService, private val imageRepo: ImageRepository) {
    @KafkaListener(topics = [KafkaConfig.TOPIC_NEW_IMAGE], containerFactory = "kafkaListenerContainerFactory")
    fun newImage(data: ConsumerRecord<String, ByteArray>) {
        val id = data.key()
        println("received image with id $id")

        val sorted = sortingService.sortPixels(data.value())

        if (ftp.upload(id, sorted))
            println("upload of sorted image $id successful")
        else
            println("upload of sorted image $id failed") // TODO: upload error to some kind of logging topic?
    }
}