package com.example.ecommerceapp.data.remote.api

import com.example.ecommerceapp.data.model.dto.ProductsDTO

interface ProductService {
    suspend fun getListOfProducts(): List<ProductsDTO>

    suspend fun getProductById(id: Int): ProductsDTO
}