package com.example.bigcart

class FoodRepository {
    private val foodService = RetrofitInstance.foodService

    suspend fun getFood(): Food {
        return foodService.getFood()
    }
}