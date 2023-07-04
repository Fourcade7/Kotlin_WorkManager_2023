package com.pr7.kotlin_workmanager_2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    fun simplework(){
        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<UploadWorker>()
                .build()

        WorkManager
            .getInstance(this@MainActivity)
            .enqueue(uploadWorkRequest)
    }
    fun periodicwork(){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(true)
            .build()

        val myRequest:PeriodicWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(
            //1, TimeUnit.HOURS, // repeatInterval (the period cycle)
            15, TimeUnit.MINUTES) // flexInterval
            .setConstraints(constraints)
            .build()

        WorkManager
            .getInstance(this@MainActivity)
            .enqueueUniquePeriodicWork(
                "mywork",
                ExistingPeriodicWorkPolicy.KEEP,
                myRequest
            )


    }
}