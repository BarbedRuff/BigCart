package com.example.bigcart.favourite

import com.google.gson.annotations.SerializedName


data class Request(
    val filter: Filter,
)

data class RequestWCursor(
    val filter: Filter,
    val start_cursor: String
)

data class Filter(
    val `property`: String,
    val rich_text: RichTextt
)

data class RichTextt(
    val contains: String
)

data class Trash(
    @SerializedName("in_trash")
    val inTrash: Boolean
)

data class Page(
    val parent: Parentt,
    val properties: Propertiess
)

data class Parentt(
    val database_id: String,
    val type: String
)

data class Propertiess(
    val food_id: FoodIdd,
    val user_id: UserIdd
)

data class FoodIdd(
    val rich_text: List<RichTexttt>,
    val type: String
)

data class UserIdd(
    val title: List<Titlee>,
    val type: String
)

data class RichTexttt(
    val text: Textt,
    val type: String
)

data class Textt(
    val content: String
)

data class Titlee(
    val text: Textt,
    val type: String
)