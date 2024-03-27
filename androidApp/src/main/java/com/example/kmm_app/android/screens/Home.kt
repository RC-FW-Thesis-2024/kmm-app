package com.example.kmm_app.android.screens

import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
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
    var locationText by remember { mutableStateOf("Fetching location...") }
    val context = LocalContext.current


    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (isActive && isRunning) {
                elapsedTime = stopwatch.getFormattedElapsedTime()
                delay(1000) // Update the time every second
            }
        }
    }

    // This effect runs once when the composable is first put on screen
    DisposableEffect(Unit) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: android.location.Location) {
                locationText = "Lat: ${location.latitude}, Lon: ${location.longitude}"
                locationManager.removeUpdates(this) // We only want the first location
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
        } catch (e: SecurityException) {
            locationText = "Location permission not granted"
        }

        // Cleanup function to remove the location listener
        onDispose {
            locationManager.removeUpdates(locationListener)
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
        Text(
            text = locationText,
            color = Color.White,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.height(25.dp))
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