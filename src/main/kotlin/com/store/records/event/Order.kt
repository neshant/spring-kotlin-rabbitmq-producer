package com.store.records.event

import com.fasterxml.jackson.annotation.JsonProperty

data class Order(
    @JsonProperty("orderId") val orderId: Long,
    @JsonProperty("orderName") val orderName: String,
    @JsonProperty("product") val product: List<Product>,
    @JsonProperty("totalQuantity") val totalQuantity: Int,
    @JsonProperty("isSameDayDelivery") val isSameDayDelivery: Boolean
)

data class Product(
    @JsonProperty("name") val name: String,
    @JsonProperty("quantity") val quantity: Int
)
