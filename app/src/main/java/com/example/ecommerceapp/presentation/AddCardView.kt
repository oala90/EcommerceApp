package com.example.ecommerceapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.R
import com.example.ecommerceapp.presentation.actions.AddCardAction
import com.example.ecommerceapp.presentation.validation.StateValidation
import com.example.ecommerceapp.presentation.visualtransformation.DigitLimitTransformation

@Composable
fun AddCardView(
    cardNumberValue: String,
    onCardNumberValueChanged: (AddCardAction.OnCardNumberChanged) -> Unit,
    cardNumberValidation: StateValidation,
    onCardNumberValidation: (String) -> Unit,
    holderNameValue: String,
    onHolderNameValueChanged: (AddCardAction.OnHolderNameChanged) -> Unit,
    holderNameValidation: StateValidation,
    onHolderNameValidation: (String) -> Unit,
    expDateValue: String,
    onExpDateValueChanged: (AddCardAction.OnExpDateChanged) -> Unit,
    expDateValidation: StateValidation,
    onExpDateValidation: (String) -> Unit,
    cvvValue: String,
    onCvvValueChanged: (AddCardAction.OnCvvChanged) -> Unit,
    cvvValidation: StateValidation,
    onCvvValidation: (String) -> Unit,
    flagAddButtonEnabled: Boolean,
    onFlagAddButtonEnabled: (Boolean) -> Unit,
    cardButtonEnabled: Boolean,
    addCardToList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        var isDialogVisible by remember { mutableStateOf(false) }

        TextField(
            value = cardNumberValue,
            onValueChange = {
                if(it.length <= 19) {
                    onCardNumberValueChanged(AddCardAction.OnCardNumberChanged(it))
                    onFlagAddButtonEnabled(false)
                    onCardNumberValidation(it)
                }
            },
            label = {
                Text(text = "Card Number")
            },
            maxLines = 1,
            visualTransformation = DigitLimitTransformation(19),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//            trailingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.camera_icon),
//                    contentDescription = "Camera icon",
//                    modifier = Modifier.clickable {
//
//                    }
//                )
//            }
        )
        if (flagAddButtonEnabled && cardNumberValidation is StateValidation.Invalid) {
            TextFieldError(textError = cardNumberValidation.error)
        }

        TextField(
            value = holderNameValue,
            onValueChange = {
                if(it.length <= 20) {
                    onHolderNameValueChanged(AddCardAction.OnHolderNameChanged(it))
                    onFlagAddButtonEnabled(false)
                    onHolderNameValidation(it)
                }
            },
            label = {
                Text(text = "Holder Name")
            },
            maxLines = 1,
            visualTransformation = DigitLimitTransformation(20),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
        if (flagAddButtonEnabled && holderNameValidation is StateValidation.Invalid) {
            TextFieldError(textError = holderNameValidation.error)
        }

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                TextField(
                    value = expDateValue,
                    onValueChange = {
                        if(it.length <= 5) {
                            onExpDateValueChanged(AddCardAction.OnExpDateChanged(it))
                            onFlagAddButtonEnabled(false)
                            onExpDateValidation(it)
                        }
                    },
                    label = {
                        Text(text = "MM/YY")
                    },
                    maxLines = 1,
                    visualTransformation = DigitLimitTransformation(5),
                    modifier = Modifier
                        .padding(end = 4.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                if (flagAddButtonEnabled && expDateValidation is StateValidation.Invalid) {
                    TextFieldError(textError = expDateValidation.error)
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                TextField(
                    value = cvvValue,
                    onValueChange = {
                        if(it.length <= 4) {
                            onCvvValueChanged(AddCardAction.OnCvvChanged(it))
                            onFlagAddButtonEnabled(false)
                            onCvvValidation(it)
                        }
                    },
                    label = {
                        Text(text = "CVV")
                    },
                    maxLines = 1,
                    visualTransformation = DigitLimitTransformation(4),
                    modifier = Modifier
                        .padding(start = 4.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                if (flagAddButtonEnabled && cvvValidation is StateValidation.Invalid) {
                    TextFieldError(textError = cvvValidation.error)
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(
                onClick = {
                    onFlagAddButtonEnabled(true)
                    isDialogVisible = if(cardButtonEnabled) {
                        addCardToList()
                        false
                    } else {
                        true
                    }
                },
                modifier = Modifier
                    .padding(6.dp)
            ) {
                Text(text = "Add")
            }
        }
        if(isDialogVisible) {
            ShowMessageDialog(
                title = "Error",
                message = "Text Fields contain errors. Please fix them",
                onDismiss = {
                    isDialogVisible = false
                }
            )
        }
    }
}

@Preview
@Composable
fun AddCardViewPreview() {
    AddCardView(
        "",
        {},
        StateValidation.Idle,
        {},
        "",
        {},
        StateValidation.Valid,
        {},
        "",
        {},
        StateValidation.Idle,
        {},
        "0",
        {},
        StateValidation.Valid,
        {},
        false,
        {},
        false,
        {}
    )
}