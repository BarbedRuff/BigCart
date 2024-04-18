package com.example.bigcart

import retrofit2.http.Headers
import retrofit2.http.POST

interface FoodService {
    @Headers(
        "Authorization: Bearer secret_pnm7IFHGiW03KO5l1XKOhgF9lw62JFVamIRADldH8jS",
        "Notion-Version: 2022-06-28",
        "Content-Type: application/json"
    )
    @POST("v1/databases/565d22c35d2f443d84814a3dacea105f/query")
    suspend fun getFood(): Food
}