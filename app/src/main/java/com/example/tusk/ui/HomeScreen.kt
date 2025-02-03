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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.tusk.data.models.TaskItem
import com.example.tusk.ui.theme.GrayColorText
import com.example.tusk.ui.theme.LightGrayText
import com.example.tusk.ui.theme.MutedGrayColor
import com.example.tusk.ui.theme.TextButtonColor
import com.example.tusk.ui.theme.TitleGrayColor


@Composable
fun HomeScreen(
    navigateToEditScreen: () -> Unit,
    todayTasks: List<TaskItem>,
    tomorrowTasks: List<TaskItem>,
    handleTaskCompleted: (TaskItem) -> Unit,
    deleteTask: (TaskItem) -> Unit
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
            navigateToEditScreen = navigateToEditScreen, todayTasks = todayTasks,
            handleTaskCompleted = handleTaskCompleted,
            deleteTask = deleteTask
        )
        //tomorrow section
        TomorrowTasks(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            navigateToEditScreen = navigateToEditScreen,
            tomorrowTasks = tomorrowTasks,
            deleteTask = deleteTask
        )

    }
}

@Composable
private fun TomorrowTasks(
    modifier: Modifier,
    navigateToEditScreen: () -> Unit,
    tomorrowTasks: List<TaskItem>,
    deleteTask: (TaskItem) -> Unit
) {
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
            if (tomorrowTasks.isEmpty()) {
                item {
                    Text(
                        text = "No tasks for tomorrow",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(tomorrowTasks) { item ->
                    InActiveTaskItem(
                        navigateToEditScreen = navigateToEditScreen,
                        taskItem = item,
                        deleteTask = {
                            deleteTask(it)
                        })
                }
            }
        }
    }
}

@Composable
private fun TodayTasks(
    modifier: Modifier,
    navigateToEditScreen: () -> Unit,
    todayTasks: List<TaskItem>,
    handleTaskCompleted: (TaskItem) -> Unit,
    deleteTask: (TaskItem) -> Unit
) {
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
            if (todayTasks.isEmpty()) {
                item {
                    Text(
                        text = "No tasks for today",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(todayTasks) { item ->
                    ActiveTaskItem(
                        navigateToEditScreen,
                        taskItem = item,
                        handleTaskCompleted = handleTaskCompleted,
                        deleteTask = {
                            deleteTask(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun InActiveTaskItem(
    navigateToEditScreen: () -> Unit,
    taskItem: TaskItem,
    deleteTask: (TaskItem) -> Unit
) {
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
                text = taskItem.title,
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight(600),
                    color = TitleGrayColor,
                )
            )
            Text(
                text = taskItem.desc,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = GrayColorText,
                )
            )
            Text(
                text = taskItem.time,
                maxLines = 2,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = LightGrayText,
                )
            )
        }
        MinimalDropdownMenu(navigateToEditScreen = navigateToEditScreen, deleteTask = {
            deleteTask(taskItem)
        })
    }
}


@Composable
private fun ActiveTaskItem(
    navigateToEditScreen: () -> Unit,
    taskItem: TaskItem,
    handleTaskCompleted: (TaskItem) -> Unit,
    deleteTask: (TaskItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            onCheckedChange = {
                handleTaskCompleted(taskItem)
            },
            checked = taskItem.isCompleted,
            colors = CheckboxDefaults.colors(
                checkmarkColor = Color.White,
                checkedColor = Color.Black,

                )
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = taskItem.title,
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight(600),
                    color = if (taskItem.isCompleted) MutedGrayColor else TitleGrayColor,
                    textDecoration =
                    if (taskItem.isCompleted)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
            )
            Text(
                text = taskItem.desc,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (taskItem.isCompleted) MutedGrayColor else GrayColorText,
                    textDecoration =
                    if (taskItem.isCompleted)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
            )
            Text(
                text = taskItem.time,
                maxLines = 2,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = if (taskItem.isCompleted) MutedGrayColor else LightGrayText,
                    textDecoration =
                    if (taskItem.isCompleted)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
                )
            )
        }
        MinimalDropdownMenu(navigateToEditScreen, deleteTask = {
            deleteTask(taskItem)
        })
    }
}

@Composable
fun MinimalDropdownMenu(navigateToEditScreen: () -> Unit, deleteTask: () -> Unit) {
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
                onClick = { deleteTask() }
            )
        }
    }
}
