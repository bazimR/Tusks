package com.example.tusk.data.state

import com.example.tusk.data.models.TaskItem

data class TaskListUiState(
    val tasks: List<TaskItem> = emptyList(),
    val hideCompleted: Boolean = false

)
