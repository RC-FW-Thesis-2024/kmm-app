package com.example.kmm_app.android.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmm_app.android.components.WorkoutCard
import com.example.kmm_app.shared.model.Workout
import com.example.kmm_app.shared.network.ApiClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllWorkoutsScreen(apiClient: ApiClient = ApiClient()) {
    val scope = rememberCoroutineScope()
    var workouts by remember { mutableStateOf(emptyList<Workout>()) }
    var isLoading by remember { mutableStateOf(true) }


    fun emptyWorkouts() {
        workouts = emptyList()
    }

    fun fetchWorkouts() {
        scope.launch {
            isLoading = true
            try {
                workouts = apiClient.getWorkouts()
            } catch (e: Exception) {
                Log.d("APIcall", "Catch $e")
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchWorkouts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CenterAlignedTopAppBar(title = { Text("Activities") },
            navigationIcon = {
                IconButton(onClick = {emptyWorkouts()}) {
                    Icon(Icons.Filled.Delete, contentDescription = "Get Location")
                }
            },
            actions = {
                IconButton(onClick = { fetchWorkouts()}) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Action Icon Description")
                }
            }
        )

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center){
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
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
}
