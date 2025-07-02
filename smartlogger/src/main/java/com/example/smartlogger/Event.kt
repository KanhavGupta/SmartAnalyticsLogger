package com.example.smartlogger

data class Event(
    val name: String,
    val attributes: Map<String, Any>,
    val timestamp: Long
)