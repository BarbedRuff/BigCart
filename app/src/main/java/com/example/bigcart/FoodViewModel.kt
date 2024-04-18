package com.example.bigcart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {
    private val repository = FoodRepository()

    private val _food = MutableLiveData<Food>()
    val food: LiveData<Food> = _food

    fun fetchFood() {
        viewModelScope.launch {
            try {
                val foods = repository.getFood()
                _food.value = foods
            } catch (e: Exception) {
                Log.d("Meow", e.toString())
            }
        }
    }
}