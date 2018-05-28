package com.goosvandenbekerom.shutter.saveservice.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate

@Configuration
@EnableKafka
class KafkaConfig {
    companion object {
        const val TOPIC_NEW_IMAGE = "new-image"
    }

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Bean
    fun consumerConfigs() = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to ByteArrayDeserializer::class.java,
            ConsumerConfig.GROUP_ID_CONFIG to "save-consumer"
    )
    @Bean
    fun consumerFactory() = DefaultKafkaConsumerFactory<String, ByteArray>(consumerConfigs())
    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, ByteArray> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, ByteArray>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}