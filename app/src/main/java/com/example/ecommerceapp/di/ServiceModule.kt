package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.remote.api.ProductService
import com.example.ecommerceapp.data.remote.api.ProductServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    internal abstract fun bindProductService(productServiceImpl: ProductServiceImpl): ProductService
}