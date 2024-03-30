package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.mapper.toEntity
import com.example.ecommerceapp.data.mapper.toListEntity
import com.example.ecommerceapp.data.model.dto.ProductsDTO
import com.example.ecommerceapp.domain.entities.ProductsEntity
import com.example.ecommerceapp.domain.repository.ProductRepository
import javax.inject.Inject

internal class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource
): ProductRepository {
    override suspend fun getListOfProducts(): List<ProductsEntity> {
        return productRemoteDataSource.getListOfProducts().toListEntity()
    }

    override suspend fun getProductById(id: Int): ProductsEntity {
        return productRemoteDataSource.getProductById(id).toEntity()
    }
}

internal interface ProductRemoteDataSource {
    suspend fun getListOfProducts(): List<ProductsDTO>
    suspend fun getProductById(id: Int): ProductsDTO
}