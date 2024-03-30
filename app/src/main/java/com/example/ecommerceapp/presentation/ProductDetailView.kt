package com.example.ecommerceapp.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.ecommerceapp.domain.entities.ProductsEntity

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductDetailView(
    product: ProductsEntity?,
    addProduct: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val context = LocalContext.current
        Text(
            text = product?.title ?: "",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = "$${product?.price ?: 0.0}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            RatingBar(
                rating = product?.rating?.rate?.toFloat() ?: 0f,
                maxRating = 5f,
            )
        }
        GlideImage(
            model = product?.image ?: Icons.Default.AccountCircle,
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )
        Text(
            text = product?.description ?: "",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show()
                    addProduct()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Add to Cart")
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Float,
    maxRating: Float = 5f,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(maxRating.toInt()) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (rating >= index + 1) Color.Yellow else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            if (index < maxRating.toInt() - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun ProductDetailViewPreview() {
    ProductDetailView(null,{})
}