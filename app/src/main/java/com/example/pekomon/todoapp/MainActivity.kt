package com.example.pekomon.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pekomon.todoapp.navigation.SetupNavigation
import com.example.pekomon.todoapp.ui.theme.ToDoAppTheme
import com.example.pekomon.todoapp.ui.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private val todoViewModel: TodoViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                navHostController = rememberNavController()
                SetupNavigation(
                    navHostController = navHostController,
                    todoViewModel = todoViewModel
                )
            }
        }
    }
}
