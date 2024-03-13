package com.example.kmm_app.android.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmm_app.shared.model.Workout
import com.example.kmm_app.shared.network.ApiClient
import kotlinx.coroutines.launch

@Composable
fun AllWorkoutsScreen(apiClient: ApiClient = ApiClient()) {
    val scope = rememberCoroutineScope()
    var workouts by remember { mutableStateOf(emptyList<Workout>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true
            try {
                workouts = apiClient.getWorkouts()
            } catch (e: Exception) {
                Log.d("APIcall","Catch $e")
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {
        Text(
            text = "All Workouts",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )

        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                items(workouts) { workout ->
                    WorkoutCard(workout)
                }
            }
        }
    }
}

@Composable
fun WorkoutCard(workout: Workout) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp)
        ) {
            Text(text = workout.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = "Duration: ${workout.duration}")
            Text(text = "Date: ${workout.date}")
        }
    }
}
