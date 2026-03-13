package cz.kutner.comicsdb.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import cz.kutner.comicsdb.ui.theme.ComicsDBTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsDBTheme {
                MainScreen()
            }
        }
    }
}
