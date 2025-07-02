package com.example.smartlogger

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

internal class EventUploader(
    private val context: Context,
    private val uploadUrl: String,
    private val queue: EventQueueManager
) {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun tryUploadPendingEvents() {
        if (!isOnline()) return

        val events = queue.getEvents()
        if (events.isEmpty()) return

        val json = gson.toJson(events)
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(uploadUrl)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Will try again later
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    queue.clearEvents()
                }
                response.close()
            }
        })
    }

    private fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnectedOrConnecting == true
    }
}
