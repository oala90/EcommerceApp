package com.example.ecommerceapp.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.entities.ProductsEntity
import com.example.ecommerceapp.domain.usecase.product.GetProductDetailsByIdUseCase
import com.example.ecommerceapp.domain.usecase.product.GetProductListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val getProductDetailsByIdUseCase: GetProductDetailsByIdUseCase
): ViewModel() {

    private val _productList = MutableStateFlow<List<ProductsEntity>>(emptyList())
    val productList = _productList.asStateFlow()

    private val _product = MutableStateFlow<ProductsEntity?>(null)
    val product = _product.asStateFlow()

    private val _cartItems = MutableStateFlow<SnapshotStateList<ProductsEntity>?>(mutableStateListOf())
    val cartItems = _cartItems.asStateFlow()

    private val _totalSum = MutableStateFlow(0.0)
    val totalSum = _totalSum.asStateFlow()

    fun getProductsList() = viewModelScope.launch(Dispatchers.Main) {
        getProductListUseCase().fold(
            { list ->
                _productList.value = list
                Log.d("Fetching list data successfully ----> :: ", list.toString())
            },
            { error ->
                Log.d("Error fetching list data ----> :: ",error.message.toString())
            }
        )
    }

    fun getProductById(id: Int) = viewModelScope.launch(Dispatchers.Main) {
        getProductDetailsByIdUseCase(id).fold(
            { product ->
                _product.value = product
                Log.d("Fetching product data successfully ----> :: ", product.toString())
            },
            { error ->
                Log.d("Error fetching product data ----> :: ",error.message.toString())
            }
        )
    }

    fun addToCart(product: ProductsEntity?) {
        val currentItems = _cartItems.value?.toMutableList()
        val existingProduct = currentItems?.find { it.id == product?.id }

        if (existingProduct != null) {
            existingProduct.quantity++
        } else {
            currentItems?.add(product!!.copy(quantity = 1))
        }
        _cartItems.value = currentItems?.toMutableStateList()
    }

    fun removeFromCart(product: ProductsEntity) {
        val currentItems = _cartItems.value?.toMutableList()
        val existingProduct = currentItems?.find { it.id == product.id }

        if (existingProduct != null) {
            existingProduct.quantity--
            if (existingProduct.quantity <= 0) {
                currentItems.remove(existingProduct)
            }
        }

        _cartItems.value = currentItems?.toMutableStateList()
    }

    fun setCartItemsList() {
        _cartItems.value = mutableStateListOf()
    }

    fun calculateTotalToPay() {
        var total = 0.0
        _cartItems.value?.forEach { product ->
            total += (product.quantity * (product.price ?: 0.0))
        }
        _totalSum.value = total
    }
}