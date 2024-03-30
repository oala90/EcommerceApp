package com.example.ecommerceapp.domain.entities

data class AddCard(
    val cardNumber: Long,
    val holderName: String,
    val expDate: String,
    val cvv: Int
)
