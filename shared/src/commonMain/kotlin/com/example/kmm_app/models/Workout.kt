package com.example.kmm_app.shared.model

import kotlinx.serialization.Serializable


@Serializable
data class Workout(
    val _id: String,
    val title: String,
    val date: String,
    val duration: String,
    val latitude: Double,
    val longitude: Double
)