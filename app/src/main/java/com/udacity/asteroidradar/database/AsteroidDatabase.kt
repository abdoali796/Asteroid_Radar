package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Asteroids::class], version = 1 , exportSchema = false)
abstract class AsteroidDatabase:RoomDatabase() {
    abstract val asteroidDatabaseDao:AsteroidDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AsteroidDatabase? =null


        fun getDatabase(context: Context):AsteroidDatabase{
            synchronized(this){
                var instance= INSTANCE

                if (instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "Asteroids"
                    ).fallbackToDestructiveMigration().build()
                }


                return instance
            }
        }
    }
}


