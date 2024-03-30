package com.example.ecommerceapp.presentation.form

data class AddCardForm(
    val cardNumber: String = "",
    val holderName: String = "",
    val expDate: String = "",
    val cvv: String = ""
)
