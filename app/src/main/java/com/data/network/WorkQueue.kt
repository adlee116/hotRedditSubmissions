package com.data.network

import androidx.work.WorkManager
import androidx.work.WorkRequest

class WorkQueue(private val workManager: WorkManager) {

    fun enqueue(workRequest: WorkRequest) {
        workManager.enqueue(workRequest)
    }
}