package com.example.tusk.ui

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tusk.ui.theme.LightGrayColorBackground
import com.example.tusk.ui.theme.TuskTheme
import com.example.tusk.ui.viewmodel.TaskViewModel

enum class TuskAppScreens(val title: String) {
    Home(title = "Home"),
    Add(title = "Add task"),
    Edit(title = "Edit task")

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuskApp(
    viewModel: TaskViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.taskListUi.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =
        TuskAppScreens.valueOf(backStackEntry?.destination?.route ?: TuskAppScreens.Home.name)
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
                HomeScreen(navigateToEditScreen = { navController.navigate(TuskAppScreens.Edit.name) })
            }
            composable(route = TuskAppScreens.Add.name) {
                InputScreen()
            }
            composable(route = TuskAppScreens.Edit.name) {
                InputScreen()
            }
        }

    }
}


@Preview(showBackground = true, name = "TuskAppPreview")
@Composable
private fun TuskAppPreview() {
    TuskTheme {
        TuskApp()
    }
}