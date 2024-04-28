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

    private val _favourite = MutableLiveData<Set<String>>()
    val favourite: LiveData<Set<String>> = _favourite

    fun fetchFavourite(userId: String, token: String) {
        viewModelScope.launch {
            try {
                val favourite = repository.getFavourite(userId, token)
                _favourite.value = favourite
            } catch (e: Exception) {
                Log.d("Error", e.toString())
                delay(5000)
                fetchFavourite(userId, token)
            }
        }
    }
}