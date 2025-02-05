package com.example.tusk.ui

import android.system.ErrnoException
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tusk.data.models.TaskItem
import com.example.tusk.ui.theme.LightGrayColorBackground
import com.example.tusk.ui.theme.TuskTheme
import com.example.tusk.ui.viewmodel.TaskViewModel

enum class TuskAppScreens(val title: String) {
    Home(title = "Home"),
    Add(title = "Add task"),
    Edit(title = "Edit task");

    companion object {
        fun fromRoute(route: String?): TuskAppScreens =
            when {
                route == null -> Home
                route.startsWith(Edit.name) -> Edit  // Handle argument case
                else -> try {
                    valueOf(route)
                } catch (e: IllegalArgumentException) {
                    Home  // Default screen if invalid route
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuskApp(
    viewModel: TaskViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.taskListUi.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TuskAppScreens.fromRoute(backStackEntry?.destination?.route)
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(LightGrayColorBackground), topBar = {
        TopAppBar(
            title = { Text(text = currentScreen.title) }
        )
    }, floatingActionButton = {
        if (currentScreen.name == TuskAppScreens.Home.name) ExtendedFloatingActionButton(
            onClick = {
                navController.navigate(TuskAppScreens.Add.name)
            },
            icon = {
                Icon(
                    Icons.Filled.Add,
                    "Extended floating action button.",
                )
            },
            text = { Text(text = "Add task") },
            containerColor = Color.Black,
            contentColor = Color.White
        )
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TuskAppScreens.Home.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = TuskAppScreens.Home.name) {
                HomeScreen(
                    todayTasks = filterTodayAndTomorrowTasks(
                        taskList = uiState.tasks,
                        isToday = true,
                        hideCompleted = uiState.hideCompleted
                    ),
                    tomorrowTasks = filterTodayAndTomorrowTasks(
                        taskList = uiState.tasks,
                        isToday = false
                    ),
                    handleTaskCompleted = { viewModel.taskComplete(it) },
                    deleteTask = {
                        viewModel.deleteTask(it.id)
                    },
                    navigateToEdit = {
                        try {
                            Log.d("nav", "TuskApp: ${TuskAppScreens.Edit.name}/$it")
                            navController.navigate("Edit/$it")
                        } catch (e: ErrnoException) {
                            Log.d("nav error", "TuskApp: $e")
                        }
                    },
                    isHideCompleted = uiState.hideCompleted,
                    hideCompleted = {
                        viewModel.hideCompleted()
                    }
                )
            }
            composable(route = TuskAppScreens.Add.name) {
                InputScreen(onDone = {
                    viewModel.addTask(it)
                    navController.navigate(TuskAppScreens.Home.name) {
                        popUpTo(TuskAppScreens.Home.name) { inclusive = true }
                    }
                })
            }
            composable(
                route = "Edit/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id")
                if (id != null) {
                    val taskItem = viewModel.getTask(id)
                    if (taskItem != null) {
                        EditScreen(taskItem = taskItem, onDone = {
                            viewModel.editTask(editedTask = it)
                            navController.navigate(TuskAppScreens.Home.name) {
                                popUpTo(TuskAppScreens.Home.name) { inclusive = true }
                            }
                        })
                    }

                } else navController.navigate(TuskAppScreens.Home.name)
            }
        }

    }
}

private fun filterTodayAndTomorrowTasks(
    isToday: Boolean,
    taskList: List<TaskItem>,
    hideCompleted: Boolean = false
): List<TaskItem> {
    if (isToday) {
        return taskList.filter { taskItem ->
            if (hideCompleted) taskItem.forToday && !taskItem.isCompleted else taskItem.forToday
        }
    }
    return taskList.filter { taskItem ->
        !taskItem.forToday
    }
}


@Preview(showBackground = true, name = "TuskAppPreview")
@Composable
private fun TuskAppPreview() {
    TuskTheme {
        TuskApp()
    }
}