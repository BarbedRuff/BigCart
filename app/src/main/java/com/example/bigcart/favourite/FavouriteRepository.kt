package com.example.bigcart.favourite

import com.example.bigcart.food.Food


class FavouriteRepository {
    private val favouriteService = RetrofitInstance.favouriteService

    suspend fun getFavourite(userId: String, token: String): Set<String> {
        var nextCursor = ""
        var favouriteList = mutableSetOf<String>()
        do{
            val partFood = when(nextCursor){
                "" -> favouriteService.getFavourite(Request(Filter("user_id", RichTextt(userId))), token)
                else -> favouriteService.getFavouriteWCursor(RequestWCursor(Filter("user_id", RichTextt(userId)), nextCursor), token)
            }
            nextCursor = partFood.nextCursor
            for(i in partFood.results){
                favouriteList.add(
                    i.properties.foodId.richText[0].plainText
                )
            }
        } while(partFood.hasMore)
        return favouriteList
    }
}