package com.example.smartlogger

import android.content.Context

object AnalyticsLogger {
    private var core: AnalyticsCore? = null

    fun init(context: Context, uploadUrl: String) {
        if (core == null)
            core = AnalyticsCore(context.applicationContext, uploadUrl)
    }

    fun logEvent(name: String, properties: Map<String, Any>) {
        core?.logEvent(name, properties)
    }
}