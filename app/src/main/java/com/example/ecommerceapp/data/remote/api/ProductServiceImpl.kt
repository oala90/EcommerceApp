package com.example.ecommerceapp.data.remote.api

import com.example.ecommerceapp.data.model.dto.ProductsDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class ProductServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : ProductService {
    override suspend fun getListOfProducts(): List<ProductsDTO> {
        return httpClient.get("https://fakestoreapi.com/products/").body()
    }

    override suspend fun getProductById(id: Int): ProductsDTO {
        return httpClient.get("https://fakestoreapi.com/products/$id").body()
    }
}