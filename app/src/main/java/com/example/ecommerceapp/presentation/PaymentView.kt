package com.example.ecommerceapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.domain.entities.AddCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun PaymentView(
    totalAmount: Double,
    cardList: List<AddCard>,
    onAddCardClicked: () -> Unit,
    onProceedClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var selectedOption by remember { mutableStateOf<AddCard?>(null) }
        var showDialog by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false)}

        Text(
            text = "Payment Summary",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Total Amount: $$totalAmount",
            style = MaterialTheme.typography.bodyMedium
        )

        Column {
            cardList.forEach { card ->
                Card(
                    modifier = Modifier
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        RadioButton(
                            selected = card == selectedOption,
                            onClick = { selectedOption = card },
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                        )
                        Column {
                            Text(text = "Card Number: ${maskedCreditCardNumber(card.cardNumber.toString())}", fontWeight = FontWeight.Bold)
                            Text(text = "Holder Name: ${card.holderName}")
                        }
                    }
                }
            }
        }

        Button(
            onClick = { onAddCardClicked() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add Card")
        }
        val scope = rememberCoroutineScope()
        Button(
            enabled = (totalAmount > 0) && selectedOption != null,
            onClick = {

                scope.launch {
                    isLoading = true
                    delay(3000)
                    isLoading = false
                    showDialog = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Proceed with Payment")
        }
        if(showDialog) {
            ShowMessageDialog(
                title = "Successful Payment",
                message = "Your payment has been successfully received. Thanks for shopping with us",
                onDismiss = {
                    showDialog = false
                    onProceedClicked()

                }
            )
        }
        if(isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

fun maskedCreditCardNumber(cardNumber: String): String {
    val visibleLength = min(4, cardNumber.length)
    val maskedPart = "•".repeat(cardNumber.length - visibleLength)
    val visiblePart = cardNumber.takeLast(visibleLength)
    return "$maskedPart$visiblePart"
}

@Preview
@Composable
fun PaymentViewPreview() {
    PaymentView(
        1102.0,
        emptyList(),
        {},
        {}
    )
}