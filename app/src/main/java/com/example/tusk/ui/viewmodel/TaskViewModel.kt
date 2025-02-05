package com.example.tusk.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.tusk.data.models.TaskItem
import com.example.tusk.data.state.TaskListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import kotlin.math.abs

private const val TAG = "TaskViewModel"

class TaskViewModel : ViewModel() {
    private val _taskListUi = MutableStateFlow(TaskListUiState())
    val taskListUi = _taskListUi.asStateFlow()


    fun hideCompleted() {
        _taskListUi.update { currentState ->
            currentState.copy(hideCompleted = !currentState.hideCompleted)
        }
    }

    fun addTask(task: TaskItem) {
        _taskListUi.update { instance ->
            instance.copy(
                tasks = instance.tasks + task.copy(
                    id = abs(
                        UUID.randomUUID().hashCode()
                    )
                )
            )
        }

        Log.d(TAG, "addTask: ${taskListUi.value.tasks}")
    }

    fun deleteTask(id: Int) {
        _taskListUi.update { instance ->
            instance.copy(tasks = instance.tasks.filter { taskItem ->
                taskItem.id != id
            })
        }
    }

    //    find a way to find task and update it
    fun editTask(editedTask: TaskItem) {
        _taskListUi.update { instance ->
            instance.copy(tasks = instance.tasks.map { taskItem ->
                if (taskItem.id == editedTask.id) {
                    editedTask
                } else {
                    taskItem
                }
            })
        }
    }

    fun taskComplete(task: TaskItem) {
        _taskListUi.update { instance ->
            instance.copy(
                tasks = instance.tasks.map { taskItem ->
                    if (task.id == taskItem.id) {
                        taskItem.copy(isCompleted = !taskItem.isCompleted)
                    } else {
                        taskItem
                    }
                }
            )
        }
    }

    //    get task with task ID
    fun getTask(id: Int): TaskItem? {
        return _taskListUi.value.tasks.find { taskItem ->
            taskItem.id == id

        }
    }
}