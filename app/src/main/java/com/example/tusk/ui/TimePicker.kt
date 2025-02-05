package com.example.tusk.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select Time") },
        text = {
            Column {
                TimePicker(state = timePickerState)
            }
        },
        confirmButton = {
            Button(onClick = {
                val hour =
                    if (timePickerState.hour > 12) timePickerState.hour % 12 else timePickerState.hour
                val minute = if (timePickerState.minute == 0) "00" else timePickerState.minute
                val amOrPm = if (timePickerState.isAfternoon) "PM" else "AM"
                val formattedTime = "$hour:$minute $amOrPm"
                Log.d("tome", "TimePickerDialog: $formattedTime")
                onConfirm(formattedTime)
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    )
}
