package com.example.bigcart.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FavouriteViewModel : ViewModel() {
    private val repository = FavouriteRepository()

    private val _favourite = MutableLiveData<MutableSet<String>>()
    lateinit var pageMapper: MutableMap<String, String>
    val favourite: LiveData<MutableSet<String>> = _favourite

    fun fetchFavourite(userId: String, token: String) {
        viewModelScope.launch {
            try {
                val (favourite, mapper) = repository.getFavourite(userId, token)
                pageMapper = mapper
                _favourite.value = favourite
            } catch (e: Exception) {
                Log.d("Error", e.toString())
                delay(5000)
                fetchFavourite(userId, token)
            }
        }
    }

    fun removeFavourite(foodId: String, token: String){
        viewModelScope.launch{
            try {
                _favourite.value = _favourite.value!!.filter { it != foodId }.toMutableSet()
                repository.delete(pageMapper[foodId]!!, token)
            } catch (e: Exception){
                Log.d("Error", e.toString())
            }
        }
    }

    fun addFavourite(foodId: String, auth: String, token: String){
        viewModelScope.launch{
            try {
                _favourite.value = _favourite.value!!.plus(foodId).toMutableSet()
                val pageId = repository.add(auth, foodId, token)
                pageMapper[foodId] = pageId
            } catch (e: Exception){
                Log.d("Error", e.toString())
            }
        }
    }

    fun contains(foodId: String): Boolean{
        return _favourite.value!!.contains(foodId)
    }
}