package cz.kutner.comicsdb.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.navigation.compose.rememberNavController
import cz.kutner.comicsdb.navigation.AppNavHost
import cz.kutner.comicsdb.ui.theme.ComicsDBTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsDBTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
