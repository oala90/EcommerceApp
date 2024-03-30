package com.example.ecommerceapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownBoxExampleView(){
    var mExpanded by remember { mutableStateOf(false) }

    // Create a list of cities
    val mCities = listOf("Queretaro", "CDMX", "Sinaloa", "Nayarit", "Yucatan")

    // Create a string value to store the selected city
    var mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Box(
        Modifier
        .padding(20.dp)
        .clickable { mExpanded = !mExpanded }) {

        // Create an Outlined Text Field
        // with icon and not expanded

        ExposedDropdownMenuBox(
            expanded = mExpanded,
            onExpandedChange = { mExpanded = it },
        ) {
            OutlinedTextField(
                value = mSelectedText,
                onValueChange = { mSelectedText = it },
                readOnly = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to
                        // the DropDown the same width
                        mTextFieldSize = coordinates.size.toSize()
                    }
                    .menuAnchor(),
                label = { Text("Label") },
                trailingIcon = {
                    Icon(
                        icon,"contentDescription",
//                        Modifier.clickable { mExpanded = !mExpanded }
                    )
                }
            )

            // Create a drop-down menu with list of cities,
            // when clicked, set the Text Field text as the city selected
            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
//                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                    .width(IntrinsicSize.Min)
            ) {
                mCities.forEach { label ->
                    DropdownMenuItem(
                        text =  { Text(text = label) },
                        onClick = {
                            mSelectedText = label
                            mExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DropDownBoxExampleViewPreview() {
    DropDownBoxExampleView()
}