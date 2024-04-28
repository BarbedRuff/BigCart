package com.example.bigcart.favourite

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
