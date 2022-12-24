package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getDatabase
import com.udacity.asteroidradar.repository.Repository


class RefreshData(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {
    companion object{
        const val WORK_NAME="RefreshData"
    }
//    the  constraint in MainActivity
    override suspend fun doWork(): Result {
        val database= getDatabase(applicationContext)
        val repository=Repository(database)
        return try {
repository.refreshDatabase()
            Result.success()
        }catch (e:Exception){
            return Result.retry()
        }
    }
}