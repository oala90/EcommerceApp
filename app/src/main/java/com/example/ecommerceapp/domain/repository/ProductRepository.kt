package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.data.model.dto.ProductsDTO
import com.example.ecommerceapp.domain.entities.ProductsEntity

interface ProductRepository {

    suspend fun getListOfProducts(): List<ProductsEntity>

    suspend fun getProductById(id: Int): ProductsEntity
}