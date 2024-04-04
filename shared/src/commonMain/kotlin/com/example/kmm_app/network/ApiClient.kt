package com.example.kmm_app.shared.network

import com.example.kmm_app.shared.model.Workout
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import com.example.kmm_app.network.env

class ApiClient {
    private val client = HttpClient()
    private val json = Json { ignoreUnknownKeys = true }
    private val apiKey = env.API_KEY
    private val baseUrl = env.DATABASE_URL

    suspend fun getWorkouts(): List<Workout> {
        val response: HttpResponse = client.post("$baseUrl/action/find") {
            header("Content-Type", "application/json")
            header("Access-Control-Request-Headers", "*")
            header("api-key", apiKey)
            header("Accept", "application/json")
            setBody( """
                {
                    "dataSource": "Cluster0",
                    "database": "Thesis",
                    "collection": "workouts"
                }
            """.trimIndent())
        }
        val responseBody: String = response.bodyAsText()
        val documents = Json.parseToJsonElement(responseBody).jsonObject["documents"]?.jsonArray
        return documents?.map { json.decodeFromString<Workout>(it.toString()) } ?: emptyList()
    }

    suspend fun setWorkout(workout: Workout) {
        val workoutJson = json.encodeToString(Workout.serializer(), workout)
        val response: HttpResponse = client.post("$baseUrl/action/insertOne") {
            header("Content-Type", "application/json")
            header("Access-Control-Request-Headers", "*")
            header("api-key", apiKey)
            header("Accept", "application/json")
            setBody( """
                {
                    "dataSource": "Cluster0",
                    "database": "Thesis",
                    "collection": "post_workouts",
                    "document": $workoutJson
                }
            """.trimIndent())
        }
        // Check the response status or handle it as needed
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Failed to post workout: ${response.status}")
        }
    }
}