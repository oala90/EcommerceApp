package com.example.ecommerceapp.data.mapper

import com.example.ecommerceapp.data.model.dto.ProductsDTO
import com.example.ecommerceapp.data.model.dto.Rating
import com.example.ecommerceapp.domain.entities.ProductsEntity
import com.example.ecommerceapp.domain.entities.RatingEntity

fun ProductsDTO.toEntity() = ProductsEntity(
    category = category,
    description = description,
    id = id,
    image = image,
    price = price,
    title = title,
    rating = rating?.toEntity()
)

fun Rating.toEntity() = RatingEntity(
    count = count,
    rate = rate
)

fun List<ProductsDTO>.toListEntity(): List<ProductsEntity> {
    return this.map { product ->
        product.toEntity()
    }
}