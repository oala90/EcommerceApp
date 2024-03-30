package com.example.ecommerceapp.domain.usecase.product

import com.example.ecommerceapp.domain.entities.ProductsEntity
import com.example.ecommerceapp.domain.repository.ProductRepository
import com.example.ecommerceapp.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetProductDetailsByIdUseCase @Inject constructor(
    private val repository: ProductRepository,
    background: CoroutineDispatcher
): UseCase<ProductsEntity, Int>(background) {
    override suspend fun run(input: Int?): ProductsEntity {
        requireNotNull(input) {"Id must not be null"}
        return repository.getProductById(input)
    }
}