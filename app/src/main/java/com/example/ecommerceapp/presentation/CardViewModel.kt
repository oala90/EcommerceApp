package com.example.ecommerceapp.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.domain.entities.AddCard
import com.example.ecommerceapp.presentation.actions.AddCardAction
import com.example.ecommerceapp.presentation.form.AddCardForm
import com.example.ecommerceapp.presentation.validation.StateValidation
import com.example.ecommerceapp.presentation.validation.StateValidation.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(

): ViewModel() {

    private val _cardForm = MutableStateFlow(AddCardForm())
    val cardForm = _cardForm.asStateFlow()

    private val _cardNumberValidationState = MutableStateFlow<StateValidation>(Idle)
    val cardNumberValidationState = _cardNumberValidationState.asStateFlow()

    private val _holderNameValidationState = MutableStateFlow<StateValidation>(Idle)
    val holderNameValidationState = _holderNameValidationState.asStateFlow()

    private val _expDateValidationState = MutableStateFlow<StateValidation>(Idle)
    val expDateValidationState = _expDateValidationState.asStateFlow()

    private val _cvvValidationState = MutableStateFlow<StateValidation>(Idle)
    val cvvValidationState = _cvvValidationState.asStateFlow()

    private val _cardButtonEnabledState = MutableStateFlow(false)
    val cardButtonEnabledState = _cardButtonEnabledState.asStateFlow()

    private val _flagCardNumberButtonState = MutableStateFlow(false)
    val flagCardNumberButtonState = _flagCardNumberButtonState.asStateFlow()

    private val _cardList = MutableStateFlow<SnapshotStateList<AddCard>>(mutableStateListOf())
    val cardList = _cardList.asStateFlow()

    fun flagCardNumberButton(flag: Boolean) {
        _flagCardNumberButtonState.value = flag
    }

    fun resetValidationStates() {
        validateCardNumber("")
        validateHolderName("")
        validateExpDate("")
        validateCvv("")
    }

    fun setCreditCardFields() {
        _cardForm.value = AddCardForm()
    }

    fun validateCardNumber(cardNumber: String) {
        _cardNumberValidationState.value = when {
            cardNumber.isBlank() -> Invalid("Please enter card number.")
            !cardNumber.matches(
//                Regex("\\b(?:\\d[ -]*?){16,19}\\b")
                Regex("\\b\\d{16,19}\\b")
            ) -> Invalid("Invalid card number. Please enter a valid one.")
//            !isLuhnValid(cardNumber) -> Invalid("Invalid card number. Please check and try again.")
            else -> Valid
        }
        addCardButtonEnabledState()
    }

    private fun isLuhnValid(cardNumber: String): Boolean {
        val reversed = cardNumber.reversed()
        val digits = reversed.map { it.toString().toInt() }
        val checksum = digits.mapIndexed { index, digit ->
            if (index % 2 == 0) digit else (digit * 2).let { if (it > 9) it - 9 else it }
        }.sum()
        return checksum % 10 == 0
    }

    fun validateHolderName(holderName: String) {
        _holderNameValidationState.value = when {
            holderName.isBlank() -> Invalid("Please enter holder name.")
            !holderName.matches(Regex("^[a-zA-Z '-]{2,30}\$")) -> Invalid("Invalid Holder Name. Please enter a valid one.")
            else -> Valid
        }
        addCardButtonEnabledState()
    }

    fun validateExpDate(expDate: String) {
        _expDateValidationState.value = when {
            expDate.isBlank() -> Invalid("Please enter the expiration date.")
            !expDate.matches(Regex("^(0[1-9]|1[0-2])\\/(\\d{2})\$")) -> Invalid("Invalid expiration date. Please enter a valid one.")
            else -> Valid
        }
        addCardButtonEnabledState()
    }

    fun validateCvv(cvv: String) {
        _cvvValidationState.value = when {
            cvv.isBlank() -> Invalid("Please enter security code.")
            !cvv.matches(Regex("^\\d{3,4}\$")) -> Invalid("Invalid security code. Please enter a valid one.")
            else -> Valid
        }
        addCardButtonEnabledState()
    }

    private fun addCardButtonEnabledState() {
        _cardButtonEnabledState.value = _cardNumberValidationState.value is Valid &&
                _holderNameValidationState.value is Valid &&
                _expDateValidationState.value is Valid &&
                _cvvValidationState.value is Valid
    }

    fun addCardToList() {
        val currentCards = _cardList.value.toMutableList()
        val existingProduct = currentCards.find { it.cardNumber == _cardForm.value.cardNumber.toLong() }

        if (existingProduct == null) {
            currentCards.add(
                AddCard(
                    _cardForm.value.cardNumber.toLong(),
                    _cardForm.value.holderName,
                    _cardForm.value.expDate,
                    _cardForm.value.cvv.toInt(),
                )
            )
        }

        _cardList.value = currentCards.toMutableStateList()
    }

    fun onFieldCardChange(action: AddCardAction) {
        when(action) {
            is AddCardAction.OnCardNumberChanged -> {
                _cardForm.update {
                    it.copy(cardNumber = action.value)
                }
            }
            is AddCardAction.OnHolderNameChanged -> {
                _cardForm.update {
                    it.copy(holderName = action.value)
                }
            }
            is AddCardAction.OnExpDateChanged -> {
                _cardForm.update {
                    it.copy(expDate = action.value)
                }
            }
            is AddCardAction.OnCvvChanged -> {
                _cardForm.update {
                    it.copy(cvv = action.value)
                }
            }
        }
    }
}