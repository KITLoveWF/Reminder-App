package com.example.remiderapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.remiderapp.Model.Task
import com.example.remiderapp.ui.theme.RemiderAppTheme
import org.intellij.lang.annotations.JdkConstants.CalendarMonth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RemiderAppTheme {
                App()
            }
        }
    }
}

@Composable
fun App()
{
    val navController = rememberNavController()
//    val context = LocalContext.current
//    context.deleteDatabase("Reminder_database")
    HideSystemBars()
    NavHost(navController = navController, startDestination = "page1")
    {
        composable("page1") {
            ListTask(navController)
        }
        composable("page2") {
            CalendarScreen(navController)
        }
        composable("page3")
        {

        }
        composable("page4") {
            CreateTask(navController)
        }
        composable( route = "page5/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })) {
                backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")
            UpdateTask(navController,taskId)
        }
        composable("page6") {

            ListFolder(navController)

        }
    }
}
@SuppressLint("InlinedApi")
@Composable
fun HideSystemBars() {
    val view = LocalView.current
    LaunchedEffect(Unit) {
        view.systemUiVisibility =
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

    }
}