package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.remote.source.ProductRemoteDataSourceImpl
import com.example.ecommerceapp.data.repository.ProductRemoteDataSource
import com.example.ecommerceapp.data.repository.ProductRepositoryImpl
import com.example.ecommerceapp.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindRemoteDataSource(remoteDataSourceImpl: ProductRemoteDataSourceImpl): ProductRemoteDataSource

    @Binds
    internal abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository
}