package com.example.ecommerceapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.ecommerceapp.domain.entities.ProductsEntity

@Composable
fun CatalogProductView(
    productList: List<ProductsEntity>,
    addToCart: (ProductsEntity) -> Unit,
    productDetails: (Int) -> Unit
) {
    Column {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            itemsIndexed(productList) { index, product ->
                if(index % 2 == 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProductItem(product = product, addToCart = addToCart, productDetails = productDetails)

                        val nextIndex = index + 1
                        if (nextIndex < productList.size) {
                            ProductItem(product = productList[nextIndex], addToCart = addToCart, productDetails = productDetails)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItem(
    product: ProductsEntity,
    addToCart: (ProductsEntity) -> Unit,
    productDetails: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(180.dp)
            .height(240.dp)
    ) {
            GlideImage(
                model = product.image,
                contentDescription = "Image Product",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clickable { productDetails(product.id!!) }
                ,
            )
            Text(
                text = product.title.toString(),
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$${product.price}",
                color = Color.Gray,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add icon",
                tint = Color.Gray,
                modifier = Modifier
                    .clickable {
                        addToCart(product)
                    }
                    .padding(
                        top = 4.dp,
                        start = 8.dp
                    )
            )
        }
    }
}

@Preview
@Composable
fun CatalogProductViewPreview() {
    CatalogProductView(
        emptyList(),
        {}
    ) {
    }
}