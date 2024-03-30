package com.example.ecommerceapp.data.remote.source

import com.example.ecommerceapp.data.model.dto.ProductsDTO
import com.example.ecommerceapp.data.remote.api.ProductService
import com.example.ecommerceapp.data.repository.ProductRemoteDataSource
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val productService: ProductService
): ProductRemoteDataSource {
    override suspend fun getListOfProducts(): List<ProductsDTO> {
        return productService.getListOfProducts()
    }

    override suspend fun getProductById(id: Int): ProductsDTO {
        return productService.getProductById(id)
    }
}