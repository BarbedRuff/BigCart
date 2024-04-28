package com.example.bigcart.food

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {
    private val repository = FoodRepository()

    private val _food = MutableLiveData<Map<String, Food>>()
    val food: LiveData<Map<String, Food>> = _food

    fun fetchFood(token: String) {
        viewModelScope.launch {
            try {
                val foods = repository.getFood(token)
                _food.value = foods
            } catch (e: Exception) {
                Log.d("MainMarket", e.toString())
                delay(5000)
                fetchFood(token)
            }
        }
    }
}
