package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.map
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsterApi
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.Asteroids
import com.udacity.asteroidradar.database.asDomainModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
enum class Filter{WEEK ,TODAY ,ALL}
class Repository(val database: AsteroidDatabase) {


   //all
val asterRepo:LiveData<List<Asteroid>> =
       Transformations.map(database.asteroidDatabaseDao.getSaveAsteroid(getToday())) {
           it.asDomainModels()
       }

    // 7days
    val asterRepo7days: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDatabaseDao.get7daysAsterid(getToday())) {
            it.asDomainModels()
        }


    //today
    val asterRepoToday: LiveData<List<Asteroid>> = Transformations.map(
        database.asteroidDatabaseDao.getTodayAsterid(
            getToday()
        )
    ) {
        it.asDomainModels()
    }

    //update database
    suspend fun refreshDatabase() {
        try {


        withContext(Dispatchers.IO) {
            val repository = AsterApi.retrofitService.getProperties()
            val JSONObjects = JSONObject(repository)
            val m = parseAsteroidsJsonResult(JSONObjects).asDatabaseModel()
            m.forEach { it -> database.asteroidDatabaseDao.insertAll(it) }
        }}catch (e:Exception){

        }
    }

    //clear database
    suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            database.asteroidDatabaseDao.clear()
        }
    }

    // pic of day
    suspend fun getPic():PictureOfDay{
    val pic = AsterApi.retrofitService.getPictuer()
    return pic
    }



}