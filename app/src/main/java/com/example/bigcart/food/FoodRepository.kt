package com.example.bigcart.food

class FoodRepository {
    private val foodService = RetrofitInstance.foodService

    suspend fun getFood(token: String): Map<String, Food> {
        var nextCursor = ""
        var foodList = mutableMapOf<String, Food>()
        do{
            val partFood = when(nextCursor){
                "" -> foodService.getFood(token)
                else -> foodService.getFoodWCursor(token, Request(nextCursor))
            }
            nextCursor = partFood.nextCursor
            for(i in partFood.results){
                foodList[i.properties.foodId.title[0].plainText] = Food(
                    label=i.properties.label.richText[0].plainText,
                    img=i.properties.image.url,
                    price=i.properties.price.number
                )
            }
        } while(partFood.hasMore)
        return foodList
    }
}