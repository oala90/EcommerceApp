package com.example.ecommerceapp.di

import com.example.ecommerceapp.domain.repository.ProductRepository
import com.example.ecommerceapp.domain.usecase.product.GetProductDetailsByIdUseCase
import com.example.ecommerceapp.domain.usecase.product.GetProductListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideGetProductListUseCase(
        repository: ProductRepository,
        background: CoroutineDispatcher
    ) = GetProductListUseCase(repository,background)

    @Singleton
    @Provides
    fun provideGetProductDetailsByIdUseCase(
        repository: ProductRepository,
        background: CoroutineDispatcher
    ) = GetProductDetailsByIdUseCase(repository,background)
}