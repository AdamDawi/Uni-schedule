package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: () -> String,
    onValueChange: (String) -> Unit,
    placeholderText: String
) {
    OutlinedTextField(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        value = text(),
        onValueChange = { onValueChange(it)},
        maxLines = 2,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            unfocusedTextColor = Color.Black,
            unfocusedPlaceholderColor = Color.Gray,
            errorCursorColor = Color.Black
        ),
        placeholder = {
            Text(
                text = placeholderText,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    )
}

@Preview
@Composable
private fun CustomTextFieldPreview() {
    CustomTextField(
        text = { "hello" },
        onValueChange = {},
        placeholderText = "schedule"
    )
}