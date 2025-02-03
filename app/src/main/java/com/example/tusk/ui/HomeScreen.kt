package com.example.tusk.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.tusk.ui.theme.GrayColorText
import com.example.tusk.ui.theme.LightGrayText
import com.example.tusk.ui.theme.MutedGrayColor
import com.example.tusk.ui.theme.TextButtonColor
import com.example.tusk.ui.theme.TitleGrayColor


@Composable
fun HomeScreen(
    navigateToEditScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
//            today section
        TodayTasks(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            navigateToEditScreen = navigateToEditScreen
        )
        //tomorrow section
        TomorrowTasks(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), navigateToEditScreen = navigateToEditScreen
        )

    }
}

@Composable
private fun TomorrowTasks(modifier: Modifier, navigateToEditScreen: () -> Unit) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Tomorrow", style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight(600)
            )
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(10) {
                InActiveTaskItem(navigateToEditScreen = navigateToEditScreen)
            }
        }
    }
}

@Composable
private fun TodayTasks(modifier: Modifier, navigateToEditScreen: () -> Unit) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), Arrangement.SpaceBetween
        ) {
            Text(
                text = "Today", style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight(600)
                )
            )
            TextButton(onClick = {}) {
                Text(
                    "Hide completed", style = MaterialTheme.typography.labelMedium.copy(
                        color = TextButtonColor
                    )
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(10) {
                ActiveTaskItem(navigateToEditScreen)
            }
        }
    }
}

@Composable
private fun InActiveTaskItem(navigateToEditScreen: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp) // Checkbox default size
            , contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(shape = MaterialTheme.shapes.extraLarge)
                    .background(Color.Black)
                // Checkbox default size
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Lorem ipsum dolor",
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight(600),
                    color = TitleGrayColor,
                )
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = GrayColorText,
                )
            )
            Text(
                text = "12:43 PM",
                maxLines = 2,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = LightGrayText,
                )
            )
        }
        MinimalDropdownMenu(navigateToEditScreen = navigateToEditScreen)
    }
}


@Composable
private fun ActiveTaskItem(navigateToEditScreen: () -> Unit) {
    var checked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .clip(shape = MaterialTheme.shapes.large)
//            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            onCheckedChange = { checked = !checked },
            checked = checked,
            colors = CheckboxDefaults.colors(
                checkmarkColor = Color.White,
                checkedColor = Color.Black,

                )
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Lorem ipsum dolor",
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight(600),
                    color = if (checked) MutedGrayColor else TitleGrayColor,
                    textDecoration =
                    if (checked)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
            )
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (checked) MutedGrayColor else GrayColorText,
                    textDecoration =
                    if (checked)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
            )
            Text(
                text = "12:43 PM",
                maxLines = 2,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = if (checked) MutedGrayColor else LightGrayText,
                    textDecoration =
                    if (checked)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
            )
        }
        MinimalDropdownMenu(navigateToEditScreen)
    }
}

@Composable
fun MinimalDropdownMenu(navigateToEditScreen: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit task") },
                onClick = { navigateToEditScreen() }
            )
            DropdownMenuItem(
                text = { Text("Option 2") },
                onClick = { /* Do something... */ }
            )
        }
    }
}
