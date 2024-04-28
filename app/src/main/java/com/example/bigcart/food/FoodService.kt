package com.example.bigcart.food

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface FoodService {
    @Headers(
        "Notion-Version: 2022-06-28",
        "Content-Type: application/json"
    )
    @POST("v1/databases/0373474a02db4465899a07e57fe4dd41/query")
    suspend fun getFood(@Header("Authorization") token: String): FoodResponse

    @Headers(
        "Notion-Version: 2022-06-28",
        "Content-Type: application/json"
    )
    @POST("v1/databases/0373474a02db4465899a07e57fe4dd41/query")
    suspend fun getFoodWCursor(@Header("Authorization") token: String, @Body body: Request): FoodResponse
}