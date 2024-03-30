package com.example.ecommerceapp.data.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductsDTO(
    val category: String?,
    val description: String?,
    val id: Int?,
    val image: String?,
    val price: Double?,
    var quantity: Int = 0,
    val rating: Rating?,
    val title: String?
)