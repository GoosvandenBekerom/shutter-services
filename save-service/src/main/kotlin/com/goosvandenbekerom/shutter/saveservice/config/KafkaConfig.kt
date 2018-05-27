package com.goosvandenbekerom.shutter.saveservice.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.IntegerSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
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
    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    // Producer
    @Bean
    fun producerConfigs() = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to IntegerSerializer::class,
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to StringSerializer::class,
            ProducerConfig.MAX_BLOCK_MS_CONFIG to 5000
    )
    @Bean
    fun producerFactory() = DefaultKafkaProducerFactory<Int, String>(producerConfigs())
    @Bean
    fun kafkaTemplate() = KafkaTemplate<Int, String>(producerFactory())

    // Consumer
    @Bean
    fun consumerConfigs() = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to IntegerDeserializer::class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class,
            ConsumerConfig.GROUP_ID_CONFIG to "save-consumer"
    )
    @Bean
    fun consumerFactory() = DefaultKafkaConsumerFactory<Int, String>(consumerConfigs())
    @Bean
    fun listenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<Int, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<Int, String>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}