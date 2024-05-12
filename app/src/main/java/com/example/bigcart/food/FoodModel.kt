package com.example.bigcart.food
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Food(
    @SerializedName("label")
    val label: String,
    @SerializedName("img")
    val img: String?,
    @SerializedName("price")
    val price: Double
): Serializable

data class FoodResponse(
    @SerializedName("developer_survey")
    val developerSurvey: String,
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("next_cursor")
    val nextCursor: String,
    @SerializedName("object")
    val objectX: String,
    @SerializedName("page_or_database")
    val pageOrDatabase: PageOrDatabase,
    @SerializedName("request_id")
    val requestId: String,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("type")
    val type: String
)

class PageOrDatabase

data class Result(
    @SerializedName("archived")
    val archived: Boolean,
    @SerializedName("cover")
    val cover: Any,
    @SerializedName("created_by")
    val createdBy: CreatedBy,
    @SerializedName("created_time")
    val createdTime: String,
    @SerializedName("icon")
    val icon: Any,
    @SerializedName("id")
    val id: String,
    @SerializedName("in_trash")
    val inTrash: Boolean,
    @SerializedName("last_edited_by")
    val lastEditedBy: LastEditedBy,
    @SerializedName("last_edited_time")
    val lastEditedTime: String,
    @SerializedName("object")
    val objectX: String,
    @SerializedName("parent")
    val parent: Parent,
    @SerializedName("properties")
    val properties: Properties,
    @SerializedName("public_url")
    val publicUrl: Any,
    @SerializedName("url")
    val url: String
)

data class CreatedBy(
    @SerializedName("id")
    val id: String,
    @SerializedName("object")
    val objectX: String
)

data class LastEditedBy(
    @SerializedName("id")
    val id: String,
    @SerializedName("object")
    val objectX: String
)

data class Parent(
    @SerializedName("database_id")
    val databaseId: String,
    @SerializedName("type")
    val type: String
)

data class Properties(
    @SerializedName("food_id")
    val foodId: FoodId,
    @SerializedName("image")
    val image: Image,
    @SerializedName("label")
    val label: Label,
    @SerializedName("price")
    val price: Price
)

data class FoodId(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: List<Title>,
    @SerializedName("type")
    val type: String
)

data class Image(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)

data class Label(
    @SerializedName("id")
    val id: String,
    @SerializedName("rich_text")
    val richText: List<RichText>,
    @SerializedName("type")
    val type: String
)

data class Price(
    @SerializedName("id")
    val id: String,
    @SerializedName("number")
    val number: Double,
    @SerializedName("type")
    val type: String
)

data class Title(
    @SerializedName("annotations")
    val annotations: Annotations,
    @SerializedName("href")
    val href: Any,
    @SerializedName("plain_text")
    val plainText: String,
    @SerializedName("text")
    val text: Text,
    @SerializedName("type")
    val type: String
)

data class Annotations(
    @SerializedName("bold")
    val bold: Boolean,
    @SerializedName("code")
    val code: Boolean,
    @SerializedName("color")
    val color: String,
    @SerializedName("italic")
    val italic: Boolean,
    @SerializedName("strikethrough")
    val strikethrough: Boolean,
    @SerializedName("underline")
    val underline: Boolean
)

data class Text(
    @SerializedName("content")
    val content: String,
    @SerializedName("link")
    val link: Any
)

data class RichText(
    @SerializedName("annotations")
    val annotations: Annotations,
    @SerializedName("href")
    val href: Any,
    @SerializedName("plain_text")
    val plainText: String,
    @SerializedName("text")
    val text: Text,
    @SerializedName("type")
    val type: String
)