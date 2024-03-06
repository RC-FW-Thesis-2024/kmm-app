package com.example.kmm_app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform