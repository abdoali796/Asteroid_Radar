package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AsteroidDatabaseDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAll(vararg :Asteroids)

   //7day query
    @Query("SELECT * from asteroid_table WHERE closeApproachDate <:today ORDER BY closeApproachDate ")
    fun getSaveAsteroid (today: String): LiveData<List<Asteroids>>

    //today query
    @Query("SELECT * from asteroid_table WHERE closeApproachDate = :today ORDER BY closeApproachDate ")
    fun getTodayAsterid(today:String):LiveData<List<Asteroids>>

    @Query("SELECT * from asteroid_table WHERE closeApproachDate >= :today ORDER BY closeApproachDate ")
    fun get7daysAsterid(today:String):LiveData<List<Asteroids>>

    //delete database
    @Query("DELETE  from asteroid_table")
    suspend fun clear()

}