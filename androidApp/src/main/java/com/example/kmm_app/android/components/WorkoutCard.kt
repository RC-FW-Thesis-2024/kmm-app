package com.example.kmm_app.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmm_app.shared.model.Workout

@Composable
fun WorkoutCard(workout: Workout) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column() {
            Text(text = workout.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Duration: ${workout.duration}",
                style = TextStyle(fontSize = 12.sp)
            )
            Text(text = "Date: ${workout.date}",
                style = TextStyle(fontSize = 12.sp)
            )
            Text(text = "Latitude: ${workout.latitude}",
                style = TextStyle(fontSize = 12.sp)
            )
            Text(text = "Longitude: ${workout.longitude}",
                style = TextStyle(fontSize = 12.sp)
            )
        }
    }
}