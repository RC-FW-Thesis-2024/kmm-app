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

class ApiClient {
    private val client = HttpClient()
    private val json = Json { ignoreUnknownKeys = true }
    private val apiKey = "QuDD0qTgxpIsOm0viZLuyCOUmRIA5pfOMwPLRCaKzZqufPgksEVq7NSLCxLkqB2b"

    suspend fun getWorkouts(): List<Workout> {
        val response: HttpResponse = client.post("https://eu-west-2.aws.data.mongodb-api.com/app/data-xcipb/endpoint/data/v1/action/find") {
            header("Content-Type", "application/json")
            header("Access-Control-Request-Headers", "*")
            header("api-key", apiKey)
            header("Accept", "application/json")
            body = """
                {
                    "dataSource": "Cluster0",
                    "database": "Thesis",
                    "collection": "workouts"
                }
            """.trimIndent()
        }
        val responseBody: String = response.readText()
        val documents = Json.parseToJsonElement(responseBody).jsonObject["documents"]?.jsonArray
        return documents?.map { json.decodeFromString<Workout>(it.toString()) } ?: emptyList()
    }
}