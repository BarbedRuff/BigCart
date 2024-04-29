package com.example.bigcart.favourite

import com.example.bigcart.favourite.page.PageModel
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface FavouriteService {
    @Headers(
        "Notion-Version: 2022-06-28",
        "Content-Type: application/json"
    )
    @POST("v1/databases/eea6f566decb4474b8f4d89579c34a1c/query")
    suspend fun getFavourite(@Body body: Request, @Header("Authorization") token: String): FavouriteResponse

    @Headers(
        "Notion-Version: 2022-06-28",
        "Content-Type: application/json"
    )
    @POST("v1/databases/eea6f566decb4474b8f4d89579c34a1c/query")
    suspend fun getFavouriteWCursor(@Body body: RequestWCursor, @Header("Authorization") token: String): FavouriteResponse

    @Headers(
        "Notion-Version: 2022-06-28",
        "Content-Type: application/json"
    )
    @PATCH("v1/pages/{page}")
    suspend fun removeFavorite(@Path("page") page: String, @Body body: Trash, @Header("Authorization") token: String)

    @Headers(
        "Notion-Version: 2022-06-28",
        "Content-Type: application/json"
    )
    @POST("v1/pages")
    suspend fun addFavorite(@Body body: Page, @Header("Authorization") token: String): PageModel

}