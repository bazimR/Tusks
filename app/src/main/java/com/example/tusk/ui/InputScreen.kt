package com.example.tusk.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tusk.data.models.TaskItem
import com.example.tusk.ui.theme.TuskTheme
import java.util.Calendar

@Composable
fun InputScreen(onDone: (TaskItem) -> Unit = {}) {
    var showTimePicker by remember { mutableStateOf(false) }
    var isToday by remember { mutableStateOf(true) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 12.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Name", style = MaterialTheme.typography.titleMedium
                )
            },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = "Description", style = MaterialTheme.typography.titleMedium
                )
            },
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(propagateMinConstraints = false, modifier = Modifier.fillMaxWidth()) {
            if (!showTimePicker) Button(onClick = { showTimePicker = true }) {
                Text(text = "set time")
            }
            if (showTimePicker) TimePickerDialog(
                onConfirm = {
                    time = it
                    showTimePicker = false
                },
                onDismiss = { showTimePicker = false })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Today :", style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight(600)
                )
            )
            Switch(checked = isToday, onCheckedChange = { isToday = !isToday })
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            val newTaskItem =
                TaskItem(
                    id = Math.random().toInt(),
                    title = name,
                    desc = description,
                    time = time,
                    forToday = isToday,
                )

            onDone(newTaskItem)
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Done", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "If you disable today, the task will be considered as tomorrow",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

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
                val minute = timePickerState.minute
                val amOrPm = if (timePickerState.isAfternoon) "pm" else "am"
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

@Preview(showBackground = true)
@Composable
private fun InputScreenPreview() {
    TuskTheme {
        InputScreen()
    }
}