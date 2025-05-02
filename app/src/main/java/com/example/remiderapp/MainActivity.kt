package com.example.remiderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    RemiderAppTheme {
//        Greeting("Android")
//    }
//}

@Composable
fun App()
{
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "page1")
    {
        composable("page1") {
            //ListTask(navController)
        }
        composable("page2") {
            //CalendarScreen(navController)
        }
        composable("page3")
        {

        }
    }
}