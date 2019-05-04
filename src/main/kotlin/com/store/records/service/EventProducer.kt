package com.store.records.service

import com.store.records.event.Address
import com.store.records.event.Customer
import com.store.records.event.Order
import com.store.records.event.Product
import mu.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

const val NEW_CUSTOMER_ROUTING_KEY = "store.customer.created"
const val NEW_ORDER_ROUTING_KEY = "store.order.created"

@Service
class EventProducer(private val rabbitTemplate: RabbitTemplate) {
    val logger = KotlinLogging.logger { }

    fun sendNewCustomerDetails() {
        logger.info("sending customer details")
        for (i in 1..5) {
            val customer = Customer(
                customerId = 472623,
                customerName = "John Doe",
                address = Address(
                    aptNo = 75,
                    streetName = " St Alphonsus St",
                    city = "Jammu",
                    isHomeAddress = true
                ),
                email = "https://goo.gl/4Db4NK"
            )
            rabbitTemplate.convertAndSend(NEW_CUSTOMER_ROUTING_KEY, customer)
        }
    }

    fun sendNewOrderDetails() {
        logger.info("sending product details")
        for (i in 1..10) {
            val order = Order(
                orderId = 4523623,
                orderName = "Flowers",
                isSameDayDelivery = true,
                product = listOf(
                    Product(name = "Roses", quantity = 25),
                    Product(name = "Plumeria", quantity = 20)
                ),
                totalQuantity = 45
            )
            rabbitTemplate.convertAndSend(NEW_ORDER_ROUTING_KEY, order)
        }
    }

    @Scheduled(fixedDelay = 1000L)
    fun executeScheduledTrips() {
        sendNewOrderDetails()
        sendNewCustomerDetails()
    }
}

