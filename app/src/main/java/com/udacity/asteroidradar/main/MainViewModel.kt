package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ApiService
import com.udacity.asteroidradar.api.AsterApi
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.Asteroids
import com.udacity.asteroidradar.repository.Filter
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(Application()) {

    private val database = AsteroidDatabase.getDatabase(application.applicationContext)
    private val repository=Repository(database)




    private val _navToDetail = MutableLiveData<Asteroid?>()
    val navToDeltali: LiveData<Asteroid?>
        get() = _navToDetail


    private val _todayPIc = MutableLiveData<PictureOfDay> ()
    val todapic: LiveData<PictureOfDay>
        get() = _todayPIc


  private var _aste=MutableLiveData<Filter>(Filter.WEEK)
   var aste: LiveData<List<Asteroid>> =Transformations.switchMap(_aste){
       when(it!!){
           Filter.WEEK ->repository.asterRepo7days
           Filter.TODAY->repository.asterRepoToday
           else->repository.asterRepo
       }
   }


init {

viewModelScope.launch {
    repository.refreshDatabase()
try {


    _todayPIc.value=repository.getPic()}
catch (e:Exception){}

}


}
    fun savedAsterid() {
        _aste.postValue(Filter.ALL)
    }
//
    fun todayAsterid() {

_aste.postValue(Filter.TODAY)


    }

    fun nextSevenAsterid(){
 _aste.postValue(Filter.WEEK)
    }



    fun onAsterodClicked(id: Asteroid) {
        _navToDetail.value = id
    }

    fun onDetailNavDon() {
        _navToDetail.value = null
    }



}