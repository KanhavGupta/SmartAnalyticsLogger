package com.example.smartlogger

import android.content.Context

class AnalyticsCore(context: Context, uploadUrl: String) {
    private val queue = EventQueueManager(context.applicationContext)
    private val uploader = EventUploader(context.applicationContext, uploadUrl, queue)

    fun logEvent(name: String, properties: Map<String, Any>) {
        val event = Event(name, properties, System.currentTimeMillis())
        queue.enqueue(event)
        uploader.tryUploadPendingEvents()
    }
}
