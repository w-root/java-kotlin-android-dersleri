package com.example.kotlin_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kotlin_compose.ui.theme.Kotlin_composeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
   SelectionContainer() {

       Column() {
           Row(Modifier.padding(PaddingValues(5.dp))) {
               Text(text = "Hello 1")
               Text(text = "Hello 2")

           }
           Row() {
               Text(text = "Hello 3")
               Text(text = "Hello 4")

           }
       }
   }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Kotlin_composeTheme {
        MainScreen()
    }
}