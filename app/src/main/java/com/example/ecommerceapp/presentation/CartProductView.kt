package com.example.ecommerceapp.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.entities.ProductsEntity

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartProductView(
    productList: List<ProductsEntity>,
    addProductIcon: (ProductsEntity) -> Unit,
    removeProductIcon: (ProductsEntity) -> Unit,
    navigateToCheckOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(productList.isEmpty()) {
            Text(text = "No items on cart")
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .height(350.dp)
                    .border(1.dp, Color.Black, ShapeDefaults.Medium)
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(productList) { productSelected ->
                        Column(
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(80.dp)
                                ) {
                                    GlideImage(
                                        model = productSelected.image,
                                        contentDescription = "Product Image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.FillBounds,
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = {
                                            removeProductIcon(productSelected)
                                        }
                                    ) {
                                        Icon(painter = painterResource(id = R.drawable.remove_icon), contentDescription = "Remove")
                                    }

                                    Text(text = "${productSelected.quantity}")

                                    IconButton(
                                        onClick = {
                                            addProductIcon(productSelected)
                                        },
                                        modifier = Modifier
                                            .clickable {
                                                addProductIcon(productSelected)
                                            }
                                    ) {
                                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    val totalProductPrice = productSelected.quantity * productSelected.price!!
                                    Text(
                                        text = "$$totalProductPrice",
                                    )
                                }
                            }
                            Text(
                                text = productSelected.title ?: "",
                                style = MaterialTheme.typography.titleSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Divider()
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                enabled = productList.isNotEmpty(),
                onClick = {
                    navigateToCheckOut()
                },
                modifier = Modifier.padding(8.dp),
            ) {
                Text(text = "Proceed to checkout")
            }
        }
    }
}

@Preview
@Composable
fun CartProductViewPreview() {
    CartProductView(
        emptyList(),
        {},
        {},
        {}
    )
}