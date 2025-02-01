package com.example.tusk.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimeInput
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
fun InputScreen(title: String, onDone: (TaskItem) -> Unit = {}) {
    var showTimePicker by remember { mutableStateOf(true) }
    var isToday by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        Text(
            text = title, style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight(600)
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth(), label = {
            Text(
                text = "Name", style = MaterialTheme.typography.titleMedium
            )
        }, singleLine = true)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth(), label = {
            Text(
                text = "Description", style = MaterialTheme.typography.titleMedium
            )
        }, maxLines = 2)
        Spacer(modifier = Modifier.height(32.dp))
        Box(propagateMinConstraints = false, modifier = Modifier.fillMaxWidth()) {
            if (!showTimePicker) Button(onClick = { showTimePicker = true }) {
                Text(text = "set time")
            }
            if (showTimePicker) TimePicker(onConfirm = {}, onDismiss = { showTimePicker = false })
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
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
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
fun TimePicker(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    Column {
        TimeInput(
            state = timePickerState,
        )
        Button(onClick = onDismiss) {
            Text("Dismiss picker")
        }
        Button(onClick = onConfirm) {
            Text("Confirm selection")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InputScreenPreview() {
    TuskTheme {
        InputScreen(title = "Add Tasks")
    }
}