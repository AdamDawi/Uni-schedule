package com.example.unischedule.presentation.main_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unischedule.common.Constants
import com.example.unischedule.ui.theme.BackgroundColor

@Composable
fun AlertDialogToWriteLink(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    onValueChange: (String) -> Unit,
    text: () -> String
) {
    AlertDialog(
        modifier = modifier
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = dialogTitle,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        },
        text = {
            CustomTextField(
                text = text,
                placeholderText = Constants.LINK_PLACEHOLDER,
                onValueChange = onValueChange
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
            ) {
                Text(
                    "Cancel",
                    color = Color.Red
                )
            }
        },
        containerColor = BackgroundColor,
        titleContentColor = Color.Black,
        textContentColor = Color.Black
    )
}

@Preview
@Composable
private fun ScheduleAlertDialogPreview() {
    AlertDialogToWriteLink(
        onDismissRequest = {  },
        onConfirmation = {  },
        dialogTitle = "Write your link to schedule",
        onValueChange = { },
        text = {"dfs"}
    )
}

@Preview
@Composable
private fun ScheduleAlertDialogLongTextPreview() {
    AlertDialogToWriteLink(
        onDismissRequest = {  },
        onConfirmation = {  },
        dialogTitle = "Write your link to schedule",
        onValueChange = { },
        text = {"dfssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssdddddd"}
    )
}

@Preview
@Composable
private fun ScheduleAlertDialogWithoutTextPreview() {
    AlertDialogToWriteLink(
        onDismissRequest = {  },
        onConfirmation = {  },
        dialogTitle = "Write your link to schedule",
        onValueChange = { },
        text = {""}
    )
}
