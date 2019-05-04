package com.store.records.event

import com.fasterxml.jackson.annotation.JsonProperty


data class Customer(
    @JsonProperty("customerId") val customerId: Long,
    @JsonProperty("customerName") val customerName: String,
    @JsonProperty("address") val address: Address,
    @JsonProperty("email") val email: String
)

data class Address(
    @JsonProperty("aptNo") val aptNo: Int,
    @JsonProperty("streetName") val streetName: String,
    @JsonProperty("city") val city: String,
    @JsonProperty("isHomeAddress") val isHomeAddress: Boolean
)
