package com.example.ecommerceapp.domain.usecase.product

import com.example.ecommerceapp.domain.entities.ProductsEntity
import com.example.ecommerceapp.domain.repository.ProductRepository
import com.example.ecommerceapp.domain.usecase.base.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val repository: ProductRepository,
    background: CoroutineDispatcher
): UseCase<List<ProductsEntity>, Unit>(background) {
    override suspend fun run(input: Unit?): List<ProductsEntity> {
        return repository.getListOfProducts()
    }
}