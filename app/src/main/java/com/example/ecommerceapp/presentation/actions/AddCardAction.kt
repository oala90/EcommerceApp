package com.example.ecommerceapp.presentation.actions

sealed interface AddCardAction {
    data class OnCardNumberChanged(val value: String): AddCardAction
    data class OnHolderNameChanged(val value: String): AddCardAction
    data class OnExpDateChanged(val value: String): AddCardAction
    data class OnCvvChanged(val value: String): AddCardAction
}