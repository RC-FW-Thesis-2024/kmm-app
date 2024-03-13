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
import androidx.compose.ui.unit.dp
import com.example.kmm_app.shared.model.Workout

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