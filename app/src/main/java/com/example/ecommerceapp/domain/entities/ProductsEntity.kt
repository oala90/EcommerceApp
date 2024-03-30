package com.example.ecommerceapp.domain.entities

data class ProductsEntity(
    val category: String?,
    val description: String?,
    val id: Int?,
    val image: String?,
    val price: Double?,
    var quantity: Int = 0,
    val rating: RatingEntity?,
    val title: String?
)