package com.example.ecommerceapp.presentation.visualtransformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DigitLimitTransformation(private val maxDigits: Int): VisualTransformation {

    private val offsetMapping: OffsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset.coerceIn(0, maxDigits)
        }

        override fun transformedToOriginal(offset: Int): Int {
            return offset
        }
    }
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length > maxDigits) {
            text.text.substring(0, maxDigits)
        } else {
            text.text
        }
        return TransformedText(AnnotatedString(trimmed), offsetMapping)
    }
}