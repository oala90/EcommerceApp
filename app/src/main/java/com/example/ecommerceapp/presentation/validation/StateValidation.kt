package com.example.ecommerceapp.presentation.validation

sealed class StateValidation {
    data object Idle: StateValidation()
    data object Valid: StateValidation()
    data class Invalid(val error: String): StateValidation()
}