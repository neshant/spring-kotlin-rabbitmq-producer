package com.store.records.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val EXCHANGE_NAME = "storeExchange"
const val CUSTOMER_RELATIONS_QUEUE = "customerRelationsDepartment"
const val ORDER_DISPATCH_QUEUE = "orderDispatchDepartment"
const val STORE_QUEUE = "allEvents"

@Configuration
class ProducerConfiguration {

    @Bean
    fun eventExchange(): TopicExchange {
        return TopicExchange(EXCHANGE_NAME, true, false)
    }

    @Bean(CUSTOMER_RELATIONS_QUEUE)
    fun relationsDepartmentQueue(): Queue {
        return Queue(CUSTOMER_RELATIONS_QUEUE, true)
    }

    @Bean(ORDER_DISPATCH_QUEUE)
    fun orderQueue(): Queue {
        return Queue(ORDER_DISPATCH_QUEUE, true)
    }

    @Bean(STORE_QUEUE)
    fun storeQueue(): Queue {
        return Queue(STORE_QUEUE, true)
    }

    @Bean
    fun customerRelations(@Qualifier(CUSTOMER_RELATIONS_QUEUE) queue: Queue, eventExchange: TopicExchange): Binding {
        return BindingBuilder
            .bind(queue)
            .to(eventExchange)
            .with("store.customer.#")
    }

    @Bean
    fun newOrders(@Qualifier(ORDER_DISPATCH_QUEUE) queue: Queue, eventExchange: TopicExchange): Binding {
        return BindingBuilder
            .bind(queue)
            .to(eventExchange)
            .with("store.order.#")
    }

    @Bean
    fun newBinding(@Qualifier(STORE_QUEUE) queue: Queue, eventExchange: TopicExchange): Binding {
        return BindingBuilder
            .bind(queue)
            .to(eventExchange)
            .with("store.#")
    }

    @Bean
    fun jackson2JsonMessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(
        connectionFactory: ConnectionFactory,
        eventExchange: TopicExchange,
        jackson2JsonMessageConverter: Jackson2JsonMessageConverter
    ): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.exchange = eventExchange.name
        rabbitTemplate.messageConverter = jackson2JsonMessageConverter
        return rabbitTemplate
    }
}
