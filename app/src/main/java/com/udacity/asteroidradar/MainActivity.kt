package com.udacity.asteroidradar

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.udacity.asteroidradar.work.RefreshData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
 val appendableScope= CoroutineScope(Dispatchers.Default)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        delayedInit()
    }
    private fun delayedInit() = appendableScope.launch {
        setWork()
    }

    //that This costraint

    private fun setWork() {
        val constraint= Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()
        val repeatingRequest =
            PeriodicWorkRequestBuilder<RefreshData>(1, TimeUnit.DAYS).setConstraints(constraint)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshData.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, repeatingRequest
        )
    }
}
