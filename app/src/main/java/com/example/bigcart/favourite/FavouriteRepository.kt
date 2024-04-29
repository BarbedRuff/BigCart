package com.example.bigcart.favourite

class FavouriteRepository {
    private val favouriteService = RetrofitInstance.favouriteService

    suspend fun getFavourite(userId: String, token: String): Pair<MutableSet<String>, MutableMap<String, String>> {
        var nextCursor = ""
        val favouriteList = mutableSetOf<String>()
        val pageMapper = mutableMapOf<String, String>()
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
                pageMapper[i.properties.foodId.richText[0].plainText] = i.id
            }
        } while(partFood.hasMore)
        return Pair(favouriteList, pageMapper)
    }

    suspend fun delete(page: String, token: String){
        favouriteService.removeFavorite(page, Trash(true), token)
    }

    suspend fun add(auth: String, foodId: String, token: String): String{
        return favouriteService.addFavorite(
            Page(
                Parentt(type="database_id", database_id="eea6f566decb4474b8f4d89579c34a1c"),
                Propertiess(
                    FoodIdd(listOf(RichTexttt(type="text", text=Textt(content=foodId))), type="rich_text"),
                    UserIdd(listOf(Titlee(type="text", text=Textt(content=auth))), type="title")
                )
            ), token).id
    }
}