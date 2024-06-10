package kr.tekit.lion.daongil.data.dto.remote.response.emergency.pharmacy


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Items(
    @Json(name = "item")
    val item: List<Item>
)