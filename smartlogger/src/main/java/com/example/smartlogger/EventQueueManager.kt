package com.example.smartlogger

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

internal class EventQueueManager(private val context: Context) {

    private val file = File(context.filesDir, "analytics_events.json")
    private val gson = Gson()
    private val lock = ReentrantLock()

    fun enqueue(event: Event) {
        lock.withLock {
            val currentEvents = readEvents().toMutableList()
            currentEvents.add(event)
            file.writeText(gson.toJson(currentEvents))
        }
    }

    fun getEvents(): List<Event> = lock.withLock {
        readEvents()
    }

    fun clearEvents() = lock.withLock {
        file.writeText("[]")
    }

    private fun readEvents(): List<Event> {
        return try {
            if (!file.exists()) return emptyList()
            val type = object : TypeToken<List<Event>>() {}.type
            gson.fromJson(file.readText(), type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
