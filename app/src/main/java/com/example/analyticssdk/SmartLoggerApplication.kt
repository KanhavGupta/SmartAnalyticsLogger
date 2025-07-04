package com.example.analyticssdk

import android.app.Application
import com.example.smartlogger.AnalyticsLogger

class SmartLoggerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize your analytics SDK
        AnalyticsLogger.init(
            context = this,
            uploadUrl = "https://webhook.site/bcad2f53-1d28-4107-a7e4-2f9e58cd4b19" // replace with actual endpoint
        )
    }
}