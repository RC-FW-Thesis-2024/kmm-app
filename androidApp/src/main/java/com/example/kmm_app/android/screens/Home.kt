package com.example.kmm_app.android.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmm_app.android.Stopwatch
import kotlinx.coroutines.*

@Composable
fun HomeScreen() {
    val stopwatch = remember { Stopwatch() }
    var elapsedTime by remember { mutableStateOf("00:00:00") }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (isActive && isRunning) {
                elapsedTime = stopwatch.getFormattedElapsedTime()
                delay(1000) // Update the time every second
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = elapsedTime,
            color = Color.White,
            style = TextStyle(
                fontSize = 55.sp, // Adjust the size as needed
                fontWeight = FontWeight.Bold // Make the text bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (!isRunning) {
                    stopwatch.start()
                    isRunning = true
                }
            }) {
                Text("Start")
            }
            Button(onClick = {
                if (isRunning) {
                    stopwatch.stop()
                    isRunning = false
                }
            }) {
                Text("Stop")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Implement save functionallity here
        }) {
            Text("Save")
        }
    }
}