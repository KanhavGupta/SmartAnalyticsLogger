package com.example.analyticssdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartlogger.AnalyticsLogger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SumWithAnalyticsUI()
            }
        }
    }
}

@Composable
fun SumWithAnalyticsUI() {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("First Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Second Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val sum = num1.toIntOrNull()?.plus(num2.toIntOrNull() ?: 0)
            result = sum
            // Replace with your actual AnalyticsLogger call
             AnalyticsLogger.logEvent("sum_clicked", mapOf("sum" to (sum ?: "error")))
        }) {
            Text("Calculate Sum")
        }

        Spacer(modifier = Modifier.height(16.dp))

        result?.let {
            Text("Result: $it", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
