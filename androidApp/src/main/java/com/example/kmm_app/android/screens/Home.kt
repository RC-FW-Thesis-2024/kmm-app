package com.example.kmm_app.android.screens

import androidx.compose.material3.*
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmm_app.android.Stopwatch
import com.example.kmm_app.shared.model.Workout
import com.example.kmm_app.shared.network.ApiClient
import kotlinx.coroutines.*
import java.util.Date
import java.util.UUID
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(apiClient: ApiClient = ApiClient()) {
    val stopwatch = remember { Stopwatch() }
    var elapsedTime by remember { mutableStateOf("00:00:00") }
    var isRunning by remember { mutableStateOf(false) }
    var locationText by remember { mutableStateOf("Location not fetched yet") }
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(isRunning) {
            while (isActive && isRunning) {
                elapsedTime = stopwatch.getFormattedElapsedTime()
                delay(1000) // Update the time every second
            }
    }

    // This effect runs once when the composable is first put on screen
    fun fetchLocation() {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: android.location.Location) {
                latitude = location.latitude
                longitude = location.longitude
                locationText = "Lat: ${location.latitude}, Long: ${location.longitude}"
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
    }


    fun postWorkout() {
        val currentWorkout = Workout(
            _id = UUID.randomUUID().toString(),
            title = "Morning Run",
            date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).format(Date()),
            duration = elapsedTime,
            latitude = latitude,
            longitude = longitude
        )

        scope.launch {
            try {
                apiClient.setWorkout(currentWorkout) // Use the updated workout instance
            } catch (e: Exception) {
                Log.d("APIcall", "Catch $e")
            }
        }
    }
    CenterAlignedTopAppBar(title = { Text("Home") },
        navigationIcon = {
            IconButton(onClick = {fetchLocation()}) {
                Icon(Icons.Filled.Place, contentDescription = "Get Location")
            }

        },
        actions = {
            IconButton(onClick = {locationText = "No location data"}) {
                Icon(Icons.Filled.Clear, contentDescription = "Reset location")
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = elapsedTime,
            style = TextStyle(
                fontSize = 55.sp, // Adjust the size as needed
                fontWeight = FontWeight.Bold // Make the text bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = locationText,
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
            postWorkout()
            elapsedTime = "00:00:00"
        }) {
            Text("Save")
        }
    }
}
