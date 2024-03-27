package com.example.kmm_app.android

class Stopwatch {
    private var startTime: Long = 0
    private var stopTime: Long = 0
    var isRunning: Boolean = false
        private set

    fun start() {
        startTime = System.currentTimeMillis()
        isRunning = true
    }

    fun stop() {
        stopTime = System.currentTimeMillis()
        isRunning = false
    }

    fun getElapsedTime(): Long {
        return if (isRunning) {
            System.currentTimeMillis() - startTime
        } else {
            stopTime - startTime
        }
    }

    fun getElapsedTimeSecs(): Long = getElapsedTime() / 1000

    fun getFormattedElapsedTime(): String {
        val totalSecs = getElapsedTimeSecs()
        val hours = totalSecs / 3600
        val minutes = (totalSecs % 3600) / 60
        val seconds = totalSecs % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}