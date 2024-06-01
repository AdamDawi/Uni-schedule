package com.example.unischedule.glance

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class UpdateWidgetWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.e("UpdateWidgetWorker", "Worker started")

        return try {
            MyAppWidget().updateAll(context = applicationContext)
            Log.e("UpdateWidgetWorker", "Widget updated")

            Result.success()
        } catch (e: Exception) {
            Log.e("UpdateWidgetWorker", "Worker failed", e)
            Result.failure()
        }
    }
}