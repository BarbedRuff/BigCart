package com.example.bigcart

data class Food(
    val `object`: String,
    val results: List<Result>,
    val next_cursor: Any,
    val has_more: Boolean,
    val type: String,
    val page_or_database: PageOrDatabase,
    val developer_survey: String,
    val request_id: String
)

data class Result(
    val `object`: String,
    val id: String,
    val created_time: String,
    val last_edited_time: String,
    val created_by: CreatedBy,
    val last_edited_by: LastEditedBy,
    val cover: Any,
    val icon: Any,
    val parent: Parent,
    val archived: Boolean,
    val in_trash: Boolean,
    val properties: Properties,
    val url: String,
    val public_url: Any
)

data class CreatedBy(
    val `object`: String,
    val id: String
)

data class LastEditedBy(
    val `object`: String,
    val id: String
)

data class Parent(
    val type: String,
    val database_id: String
)

data class Properties(
    val image: Image,
    val label: Label,
    val food_id: FoodId
)

data class Image(
    val id: String,
    val type: String,
    val url: String?
)

data class Label(
    val id: String,
    val type: String,
    val rich_text: List<RichText>
)

data class FoodId(
    val id: String,
    val type: String,
    val title: List<Title>
)

data class RichText(
    val type: String,
    val text: Text,
    val annotations: Annotations,
    val plain_text: String,
    val href: Any
)

data class Text(
    val content: String,
    val link: Any
)

data class Annotations(
    val bold: Boolean,
    val italic: Boolean,
    val strikethrough: Boolean,
    val underline: Boolean,
    val code: Boolean,
    val color: String
)

data class Title(
    val type: String,
    val text: Text,
    val annotations: Annotations,
    val plain_text: String,
    val href: Any
)

class PageOrDatabase